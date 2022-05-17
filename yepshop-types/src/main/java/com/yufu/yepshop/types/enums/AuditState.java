package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 12:16
 */
@AllArgsConstructor
@Getter
public enum AuditState {
    /**
     * PENDING 待审核
     * BLOCK 屏蔽
     * SUCCESS 审核成功
     */
    PENDING,
    BLOCK,
    SUCCESS
}
