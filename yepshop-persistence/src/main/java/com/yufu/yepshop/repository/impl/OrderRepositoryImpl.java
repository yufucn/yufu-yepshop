package com.yufu.yepshop.repository.impl;

import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderItemDO;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.repository.OrderRepository;
import com.yufu.yepshop.types.dto.OrderDTO;
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
    public OrderDTO save(OrderDTO entity) {
        OrderDO orderDO = converter.toDO(entity);
        for (OrderItemDO doo :
                orderDO.getItems()) {
            doo.setOrder(orderDO);
        }
        orderDAO.save(orderDO);
        return converter.toDTO(orderDO);
    }
}
