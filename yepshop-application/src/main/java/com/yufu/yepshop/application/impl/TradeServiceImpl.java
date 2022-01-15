package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.external.ExternalWeChatPayService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.repository.OrderRepository;
import com.yufu.yepshop.repository.TradeRepository;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.command.CreateOrderItemCommand;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import com.yufu.yepshop.types.enums.OrderState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wang
 * @date 2022/1/12 0:23
 */
@Service
public class TradeServiceImpl extends BaseService implements TradeService {

    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;
    private final ExternalWeChatPayService externalWeChatPayService;

    public TradeServiceImpl(OrderRepository orderRepository,
                            TradeRepository tradeRepository,
                            ExternalWeChatPayService externalWeChatPayService) {
        this.orderRepository = orderRepository;
        this.tradeRepository = tradeRepository;
        this.externalWeChatPayService = externalWeChatPayService;
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
            trade = tradeRepository.save(trade);
            OrderDTO order = new OrderDTO();
            order.setTradeId(trade.getId());
            order.setSellerId(orderCommand.getSellerId());
            order.setSellerType(orderCommand.getSellerType());
            order.setBuyerId(user.getId().toString());
            order.setTotalFee(orderCommand.getTotalFee());
            order.setPayment(orderCommand.getPayment());
            // todo:调试解决默认支付成功
            order.setOrderState(OrderState.WAIT_SELLER_SEND_GOODS);
            order.setDeliveryAddress(command.getDeliveryAddress());
            List<OrderItemDTO> items = new ArrayList<>();
            for (CreateOrderItemCommand itemCommand : orderCommand.getItems()) {
                OrderItemDTO item = new OrderItemDTO();
                item.setGoods(itemCommand.getGoods());
                item.setNum(itemCommand.getNum());
                items.add(item);
            }
            order.setItems(items);
            OrderDTO t = orderRepository.save(order);
            trade.setOrder(t);
            trades.add(trade);
        }
//        externalWeChatPayService.pay(trades);
        return Result.success("测试阶段，默认买家已付款！");
    }
}
