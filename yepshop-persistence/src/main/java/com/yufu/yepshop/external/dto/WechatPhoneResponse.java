package com.yufu.yepshop.external.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/20 23:19
 */
@Getter
@Setter
public class WechatPhoneResponse {
    private Integer errcode;
    private String errmsg;
    private WechatPhoneInfo phone_info;

}
