package com.yufu.yepshop.repository;

import com.yufu.yepshop.types.dto.OrderDTO;

/**
 * @author wang
 * @date 2022/1/12 22:00
 */
public interface OrderRepository {
    OrderDTO save(OrderDTO entity);
}
