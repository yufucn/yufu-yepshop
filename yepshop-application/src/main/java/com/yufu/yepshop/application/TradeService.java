package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;

/**
 * @author wang
 * @date 2022/1/12 0:23
 */
public interface TradeService {
    Result<Boolean> checkout(CheckoutCommand command);


//    // 下单
//    BuyerOrderDTO checkout(@Valid CheckoutCommand cmd);
//
//    // 支付成功
//    BuyerOrderDTO payReceived(@Valid PaymentReceivedEvent event);
//
//    // 支付取消
//    BuyerOrderDTO payCanceled(@Valid PaymentCanceledEvent event);
//
//    // 发货
//    BuyerOrderDTO packageSent(@Valid PackageSentEvent event);
//
//    // 收货
//    BuyerOrderDTO delivered(@Valid DeliveredEvent event);
//
//    // 批量查询
//    List<BuyerOrderDTO> query(OrderQuery query);
//
//    // 单个查询
//    BuyerOrderDTO getOrder(Long orderId);
}
