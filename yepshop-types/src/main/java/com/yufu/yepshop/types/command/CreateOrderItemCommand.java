package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.GoodsValue;
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

    @ApiModelProperty(value = "商品")
    private GoodsValue goods;

    @ApiModelProperty(value = "商品数量")
    private Integer num;
}
