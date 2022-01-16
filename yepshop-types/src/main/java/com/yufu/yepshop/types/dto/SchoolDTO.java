package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.AddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;

/**
 * @author wang
 * @date 2022/1/16 21:51
 */
@Getter
@Setter
public class SchoolDTO {

    @ApiModelProperty(value = "学校id")
    private String id;

    @ApiModelProperty(value = "学校名称")
    private String name;

    @ApiModelProperty(value = "学校简介")
    private String abbreviation;

    @ApiModelProperty(value = "学校地址")
    private AddressValue address;

    @ApiModelProperty(value = "学校排序字段")
    private Integer sortId;
}
