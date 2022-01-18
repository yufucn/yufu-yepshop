package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/18 21:55
 */
@AllArgsConstructor
@Getter
public enum OrderRateResult {
    /**
     * 评价结果,可选值:good(好评),neutral(中评),bad(差评)
     */
    GOOD,
    NEUTRAL,
    BAD;
}
