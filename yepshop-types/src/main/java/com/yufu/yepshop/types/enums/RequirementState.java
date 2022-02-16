package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/2/15 23:32
 */
@AllArgsConstructor
@Getter
public enum RequirementState {
    /**
     * 求购状态
     */
    UP,
    BOUGHT,
    DRAFT;
}
