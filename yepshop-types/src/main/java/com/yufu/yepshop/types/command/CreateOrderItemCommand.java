package com.yufu.yepshop.types.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/13 21:15
 */
@Getter
@Setter
public class CreateOrderItemCommand {
    @ApiModelProperty(value = "商品Id")
    private String goodId;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品图片")
    private String picUrl;

    @ApiModelProperty(value = "商品单价")
    private Integer price;

    @ApiModelProperty(value = "商品数量")
    private Integer num;
}
