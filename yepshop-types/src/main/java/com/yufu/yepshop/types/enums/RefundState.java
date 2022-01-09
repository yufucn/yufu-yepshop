package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 12:00
 */
@AllArgsConstructor
@Getter
public enum RefundState {
    /**
     *	退款状态。退款状态。可选值
     *	WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
     *	WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
     *	WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
     *	SELLER_REFUSE_BUYER(卖家拒绝退款)
     *	CLOSED(退款关闭)
     *	SUCCESS(退款成功)
     */
    WAIT_SELLER_AGREE,
    WAIT_BUYER_RETURN_GOODS,
    SELLER_REFUSE_BUYER,
    SUCCESS,
    CLOSED
    ;
}
