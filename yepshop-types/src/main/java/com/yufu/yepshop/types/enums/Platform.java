package com.yufu.yepshop.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang
 * @date 2022/2/11 21:37
 */
@AllArgsConstructor
@Getter
public enum Platform {
    /**
     * WECHATMINAPP 微信小程序
     * PC pc端
     * APP app端
     */
    WECHATMINAPP,
    PC,
    APP,
}
