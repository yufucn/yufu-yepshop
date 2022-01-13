package com.yufu.yepshop.repository;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.domain.ordering.Order;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/12 22:00
 */
public interface OrderRepository {
    Long save(Order entity);
}
