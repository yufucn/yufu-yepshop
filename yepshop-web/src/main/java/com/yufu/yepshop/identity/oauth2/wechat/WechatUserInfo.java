package com.yufu.yepshop.identity.oauth2.wechat;

import lombok.Data;

/**
 * @author wang
 * @date 2022/1/5 0:45
 */
@Data
public class WechatUserInfo {
    private String avatarUrl;

    private String city;

    private String country;

    private Integer gender;

    private String language;

    private String nickName;

    private String province;

}
