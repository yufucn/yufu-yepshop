package com.yufu.yepshop.types.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 11:17
 */
@AllArgsConstructor
@Getter
public enum GoodsState {
    /**
     * 闲置状态
     */
    UP,
    DOWN,
    SOLD,
    DRAFT;
}
