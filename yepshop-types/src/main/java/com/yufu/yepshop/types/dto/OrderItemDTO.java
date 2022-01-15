package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.GoodsValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @author wang
 * @date 2022/1/15 13:06
 */
@Getter
@Setter
public class OrderItemDTO {

    @ApiModelProperty(value = "商品")
    private GoodsValue goods;

    private Integer num;
}
