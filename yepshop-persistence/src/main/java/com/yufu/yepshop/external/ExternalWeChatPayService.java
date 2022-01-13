package com.yufu.yepshop.external;

import com.yufu.yepshop.domain.ordering.Order;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/13 23:29
 */
public interface ExternalWeChatPayService {
    String pay(String openId,List<Order> orders);
}
