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
    @ApiModelProperty(value = "用户手机号")
    private String mobile;
}
