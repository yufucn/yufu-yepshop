package com.yufu.yepshop.external.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/5/9 20:53
 */
@Getter
@Setter
public class WechatPayResponse {
    private WechatPayData pay;
    private String code;
    private String message;
}
