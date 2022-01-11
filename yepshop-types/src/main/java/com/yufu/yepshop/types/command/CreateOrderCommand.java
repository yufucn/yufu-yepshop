package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.AddressValue;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wang
 * @date 2022/1/11 23:07
 */
@Getter
@Setter
public class CreateOrderCommand {

    @ApiModelProperty(value = "商品Id")
    private String goodId;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品图片")
    private String picUrl;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品数量")
    private Integer num;

    @ApiModelProperty(value = "实付款")
    private BigDecimal payment;

    @ApiModelProperty(value = "邮费")
    private BigDecimal postFee;

    @ApiModelProperty(value = "收货地址")
    private DeliveryAddressValue address;
}
