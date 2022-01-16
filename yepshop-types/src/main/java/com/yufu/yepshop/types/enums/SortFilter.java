package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/16 21:17
 */
@AllArgsConstructor
@Getter
public enum SortFilter {
    /**
     *
     */
    ALL("综合"),
    PRICE_ASC("价格升序"),
    PRICE_DESC("价格降序"),
    LATEST("最新发布");
    private final String message;

}
