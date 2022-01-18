package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/18 21:33
 */
@AllArgsConstructor
@Getter
public enum RateState {
    /**
     * 评价状态
     */
    RATE_UN_BUYER("买家未评"),
    RATE_UN_SELLER("卖家未评"),
    RATE_BUYER_UN_SELLER("买家已评，卖家未评"),
    RATE_UN_BUYER_SELLER("买家未评，卖家已评"),
    RATE_BUYER_SELLER("买家已评,卖家已评");
    private final String message;
}
