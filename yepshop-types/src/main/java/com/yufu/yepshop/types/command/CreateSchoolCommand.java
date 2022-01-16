package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.AddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/8 12:45
 */

@Getter
@Setter
public class CreateSchoolCommand {

    @ApiModelProperty(value = "学校名称")
    private String name;

    @ApiModelProperty(value = "学校简介")
    private String abbreviation;

    @ApiModelProperty(value = "学校地址")
    private AddressValue address;

    @ApiModelProperty(value = "学校排序字段")
    private Integer sortId;
}
