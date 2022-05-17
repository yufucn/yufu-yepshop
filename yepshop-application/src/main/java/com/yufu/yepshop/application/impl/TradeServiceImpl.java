package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.external.dto.WechatPayResponse;
import com.yufu.yepshop.external.impl.ExternalWeChatPayServiceImpl;
import com.yufu.yepshop.persistence.DO.*;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.converter.TradeConverter;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.persistence.dao.TradeDAO;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.command.CreateOrderItemCommand;
import com.yufu.yepshop.types.command.PayCommand;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import com.yufu.yepshop.types.enums.PayState;
import com.yufu.yepshop.types.event.PaymentReceivedEvent;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;


/**
 * @author wang
 * @date 2022/1/12 0:23
 */
@Service
public class TradeServiceImpl extends BaseService implements TradeService {

    //    private final OrderRepository orderRepository;
//    private final TradeRepository tradeRepository;
    private final OrderDAO orderDAO;
    private final TradeDAO tradeDAO;
    private final GoodsDAO goodsDAO;
    private final TradeConverter tradeConverter = TradeConverter.INSTANCE;
    private final OrderConverter orderConverter = OrderConverter.INSTANCE;
    private final ExternalWeChatPayServiceImpl externalWeChatPayService;

    public TradeServiceImpl(
//            OrderRepository orderRepository,
//                            TradeRepository tradeRepository,
            OrderDAO orderDAO,
            TradeDAO tradeDAO,
            GoodsDAO goodsDAO,
            ExternalWeChatPayServiceImpl externalWeChatPayService
    ) {
//        this.orderRepository = orderRepository;
//        this.tradeRepository = tradeRepository;
        this.orderDAO = orderDAO;
        this.tradeDAO = tradeDAO;
        this.goodsDAO = goodsDAO;
        this.externalWeChatPayService = externalWeChatPayService;
    }

    @Override
    public Result<WechatPayResponse> checkout(CheckoutCommand command) {
        UserAccountDO user = currentUser();
        List<TradeDTO> trades = new ArrayList<>();
        List<CreateOrderCommand> orders = command.getOrders();
        for (CreateOrderCommand orderCommand : orders) {
            TradeDTO trade = new TradeDTO();
            trade.setPayType(command.getPayType());
            trade.setOpenId(user.getOpenId());
            TradeDO doo = tradeConverter.toDO(trade);
            tradeDAO.save(doo);
            trade.setId(doo.getId().toString());

            OrderDTO order = new OrderDTO();
            order.setTradeId(trade.getId());
            Seller seller = new Seller();
            seller.setId(orderCommand.getSellerId());
            seller.setType(orderCommand.getSellerType());
            order.setSeller(seller);
            Buyer buyer = new Buyer();
            buyer.setId(user.getId().toString());
            order.setBuyer(buyer);

            order.setPostFee(orderCommand.getPostFee());
            order.setTotalFee(orderCommand.getTotalFee());

            order.setPayment(orderCommand.getPayment());
            order.setDeliveryAddress(command.getDeliveryAddress());

            List<OrderItemDTO> items = new ArrayList<>();
            for (CreateOrderItemCommand itemCommand : orderCommand.getItems()) {
                OrderItemDTO item = new OrderItemDTO();
                item.setGoods(itemCommand.getGoods());
                item.setNum(itemCommand.getNum());
                items.add(item);
            }
            order.setItems(items);
            OrderDO orderDO = orderConverter.toDO(order);
            for (OrderItemDO itemDO :
                    orderDO.getItems()) {
                itemDO.setOrder(orderDO);
            }
            orderDO.prePay();
            orderDAO.save(orderDO);

            trade.setOrder(orderConverter.toDTO(orderDO));
            trades.add(trade);
        }
        WechatPayResponse payId = externalWeChatPayService.pay(trades);
        return Result.success(payId);
    }

    @Override
    public Result<Boolean> paySuccess(PaymentReceivedEvent event) throws ParseException {
        Long tradeId = Long.parseLong(event.getOutTradNo());
        Optional<TradeDO> tradeOptional = tradeDAO.findById(tradeId);
        if (!tradeOptional.isPresent()) {
            return Result.fail("交易不存在");
        }
        TradeDO tradeDO = tradeOptional.get();
        if (tradeDO.hasPayed()) {
            return Result.fail("交易已支付");
        }
        Date payTime = new Date();
        tradeDO.setPayTime(payTime);
        tradeDO.setPayState(PayState.TAY_SUCCESS);
        tradeDO.setCombineId(event.getTransactionId());
        tradeDAO.save(tradeDO);

        OrderDO order = orderDAO.findByTradeId(tradeId);
        order.pay();
        order.setPayTime(payTime);
        orderDAO.save(order);
        return Result.success(true);
    }

    @Override
    public Result<WechatPayResponse> pay(PayCommand command) {
        List<TradeDTO> trades = new ArrayList<>();
        Long id = Long.parseLong(command.getTradeId());
        Optional<TradeDO> t = tradeDAO.findById(id);
        if (!t.isPresent()) {
            return Result.fail("交易不存在！");
        }
        TradeDO tradeDO = t.get();
        if (tradeDO.hasPayed()) {
            return Result.fail("订单已支付！");
        }
        OrderDO orderDO = orderDAO.findByTradeId(id);
        TradeDTO dto = tradeConverter.toDTO(tradeDO);
        OrderDTO orderDto = orderConverter.toDTO(orderDO);

        OrderItemDTO item = orderDto.getItems().get(0);
        Long goodsId = Long.parseLong(item.getGoods().getId());
        Optional<GoodsDO> goodOptional = goodsDAO.findById(goodsId);
        if (!goodOptional.isPresent()) {
            tradeDO.payCancel();
            tradeDAO.save(tradeDO);
            orderDO.closeBySystem();
            orderDAO.save(orderDO);
            return Result.fail("闲置已被删除！");
        }
        GoodsDO goods = goodOptional.get();
        if (!goods.canBuy()) {
            tradeDO.payCancel();
            tradeDAO.save(tradeDO);
            orderDO.closeBySystem();
            orderDAO.save(orderDO);
            return Result.fail("闲置已被卖出！");
        }

        dto.setOrder(orderDto);
        trades.add(dto);
        WechatPayResponse payId = externalWeChatPayService.pay(trades);
        return Result.success(payId);
    }
}
