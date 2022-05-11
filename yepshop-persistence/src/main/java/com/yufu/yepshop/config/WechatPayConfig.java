package com.yufu.yepshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wang
 * @date 2022/5/8 23:26
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Getter
@Setter
public class WechatPayConfig {
    private String privateKeyPath;
    private String mchId;
    private String mchSerialNo;
    private String notifyUrl;
    private String appid;
    private String apiV3Key;

}
