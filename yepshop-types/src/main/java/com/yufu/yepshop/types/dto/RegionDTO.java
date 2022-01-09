package com.yufu.yepshop.types.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 13:55
 */
@Getter
@Setter
public class RegionDTO {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "行政区域名称")
    private String name;

    @ApiModelProperty(value = "行政区域类型，1是省， 2是市，3是区县")
    private Integer type;

    @ApiModelProperty(value = "行政区域编码")
    private String code;
}
