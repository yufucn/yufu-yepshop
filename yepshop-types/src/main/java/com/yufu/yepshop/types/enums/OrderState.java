package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/8 10:51
 */
@AllArgsConstructor
@Getter
public enum OrderState {
    /**
     * 等待买家付款
     */
    WAIT_BUYER_PAY("等待买家付款"),
    WAIT_SELLER_SEND_GOODS("买家已付款"),
    WAIT_BUYER_CONFIRM_GOODS("卖家已发货"),
    TRADE_FINISHED("交易成功"),
    TRADE_CLOSED("交易关闭"),
    TRADE_CLOSED_BY_SYSTEM("交易系统关闭");
    private final String message;
}
