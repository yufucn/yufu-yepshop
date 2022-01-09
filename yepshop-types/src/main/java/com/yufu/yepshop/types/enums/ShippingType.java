package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/1/8 11:31
 */
@AllArgsConstructor
@Getter
public enum ShippingType {
    /**
     * 物流方式
     * free(卖家承担运费),post(平邮),express(快 递),ems(EMS),virtual(虚拟发货)
     */
    FREE,
    POST,
    EXPRESS,
    EMS,
    VIRTUAL;
}
