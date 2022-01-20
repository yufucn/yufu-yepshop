package com.yufu.yepshop.external.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/20 23:20
 */
@Getter
@Setter
public class WechatPhoneInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
}
