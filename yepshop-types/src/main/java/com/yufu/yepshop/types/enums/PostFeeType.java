package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 11:31
 */
@AllArgsConstructor
@Getter
public enum PostFeeType {
    /**
     * 运费方式
     * FREE(卖家承担运费),COLLECT(到付)
     */
    FREE,
    COLLECT
}
