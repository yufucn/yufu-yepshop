package com.yufu.yepshop.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.*;
import java.util.Base64;

/**
 * @author wang
 * @date 2022/5/9 21:32
 */
@Setter
@Getter
public class WechatPayData {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    @JsonProperty("package")
    private String packageInfo;
    private String signType;
    private String paySign;

    public String paySignStr() {
        return this.getAppId() + "\n" +
                this.getTimeStamp() + "\n" +
                this.getNonceStr() + "\n" +
                this.getPackageInfo() + "\n"
                ;

    }
}
