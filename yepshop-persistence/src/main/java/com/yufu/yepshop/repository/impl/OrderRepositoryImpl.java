package com.yufu.yepshop.repository.impl;

import com.yufu.yepshop.domain.ordering.Order;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.repository.OrderRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/12 22:00
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDAO orderDAO;
    private final OrderConverter converter = OrderConverter.INSTANCE;

    public OrderRepositoryImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public Long save(Order entity) {
        OrderDO orderDO = converter.toDO(entity);
        orderDAO.save(orderDO);
        return orderDO.getId();
    }
}
