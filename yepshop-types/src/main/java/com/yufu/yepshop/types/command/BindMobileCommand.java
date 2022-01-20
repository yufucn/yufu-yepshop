package com.yufu.yepshop.types.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/16 22:42
 */
@Getter
@Setter
public class BindMobileCommand {
    @ApiModelProperty(value = "基础库 2.21.2 使用，动态令牌。可通过动态令牌换取用户手机号")
    private String code;

    private String encryptedData;

    private String iv;
}
