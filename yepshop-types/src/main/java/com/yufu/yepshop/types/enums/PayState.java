package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 10:53
 */
@AllArgsConstructor
@Getter
public enum PayState {
    /**
     * 微信支付 小程序支付
     */
    TAY_CANCEL("付款取消"),
    TAY_SUCCESS("付款成功"),
    TAY_FAIL("付款失败"),
    TAY_TIMEOUT("支付超时");
    private final String message;
}
