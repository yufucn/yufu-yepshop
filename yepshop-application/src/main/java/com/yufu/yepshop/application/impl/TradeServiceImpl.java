package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.external.dto.WechatPayResponse;
import com.yufu.yepshop.external.impl.ExternalWeChatPayServiceImpl;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderItemDO;
import com.yufu.yepshop.persistence.DO.TradeDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.converter.TradeConverter;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.persistence.dao.TradeDAO;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.command.CreateOrderItemCommand;
import com.yufu.yepshop.types.command.PayCommand;
import com.yufu.yepshop.types.dto.CheckoutDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import com.yufu.yepshop.types.enums.PayState;
import com.yufu.yepshop.types.event.PaymentReceivedEvent;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.Seller;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final TradeConverter tradeConverter = TradeConverter.INSTANCE;
    private final OrderConverter orderConverter = OrderConverter.INSTANCE;
    private final ExternalWeChatPayServiceImpl externalWeChatPayService;

    public TradeServiceImpl(
//            OrderRepository orderRepository,
//                            TradeRepository tradeRepository,
            OrderDAO orderDAO,
            TradeDAO tradeDAO,
            ExternalWeChatPayServiceImpl externalWeChatPayService
    ) {
//        this.orderRepository = orderRepository;
//        this.tradeRepository = tradeRepository;
        this.orderDAO = orderDAO;
        this.tradeDAO = tradeDAO;
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

        List<OrderDO> orders = orderDAO.findByTradeId(tradeId);
        for (OrderDO order :
                orders) {
            order.pay();
            order.setPayTime(payTime);
            orderDAO.save(order);
        }
        return Result.success(true);
    }

    @Override
    public Result<WechatPayResponse> pay(PayCommand command) {
        List<TradeDTO> trades = new ArrayList<>();
        Optional<TradeDO> t = tradeDAO.findById(command.getTradeId());
        if (!t.isPresent()){
            return Result.fail("交易不存在！");
        }
        TradeDO tradeDO = t.get();
        List<OrderDO> orders = orderDAO.findByTradeId(command.getTradeId());
        TradeDTO dto = tradeConverter.toDTO(tradeDO);
        List<OrderDTO> ordersDto = orderConverter.toDTO()
        WechatPayResponse payId = externalWeChatPayService.pay(trades);
        return Result.success(payId);
    }
}
