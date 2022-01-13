package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.domain.ordering.Order;
import com.yufu.yepshop.domain.ordering.OrderItem;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.repository.GoodsRepository;
import com.yufu.yepshop.repository.OrderRepository;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.command.CreateOrderItemCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wang
 * @date 2022/1/12 0:23
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    private final GoodsRepository goodsRepository;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(GoodsRepository goodsRepository,
                            OrderRepository orderRepository) {
        this.goodsRepository = goodsRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Result<Boolean> checkout(CheckoutCommand command) {
        UserAccountDO user = currentUser();
        for (CreateOrderCommand orderCommand : command.getOrders()) {
            Order order = new Order();
            order.setSellerId(orderCommand.getSellerId());
            order.setSellerType(orderCommand.getSellerType());
            order.setBuyerId(user.getId());
            order.setDeliveryAddress(command.getDeliveryAddress());
            order.setPayType(command.getPayType());
            List<OrderItem> items = new ArrayList<>();
            for (CreateOrderItemCommand itemCommand : orderCommand.getItems()) {
                OrderItem item = new OrderItem();
                item.setGoodsId(itemCommand.getGoodId());
                item.setGoodsPicUrl(itemCommand.getPicUrl());
                item.setGoodsTitle(itemCommand.getTitle());
                item.setPrice(itemCommand.getPrice());
                item.setNum(itemCommand.getNum());
                items.add(item);
            }
            order.setItems(items);
            orderRepository.save(order);
        }
        return Result.success();
    }
}
