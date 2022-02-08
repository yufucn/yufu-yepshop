package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
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
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
//    private final ExternalWeChatPayService externalWeChatPayService;

    public TradeServiceImpl(
//            OrderRepository orderRepository,
//                            TradeRepository tradeRepository,
            OrderDAO orderDAO, TradeDAO tradeDAO
//            ExternalWeChatPayService externalWeChatPayService
    ) {
//        this.orderRepository = orderRepository;
//        this.tradeRepository = tradeRepository;
        this.orderDAO = orderDAO;
        this.tradeDAO = tradeDAO;
//        this.externalWeChatPayService = externalWeChatPayService;
    }

    @Override
    public Result<Boolean> checkout(CheckoutCommand command) {
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
            // todo:调试解决默认支付成功
            orderDO.pay();
            orderDAO.save(orderDO);

            trade.setOrder(orderConverter.toDTO(orderDO));
            trades.add(trade);
        }
//        externalWeChatPayService.pay(trades);
        return Result.success("测试阶段，默认买家已付款！");
    }
}
