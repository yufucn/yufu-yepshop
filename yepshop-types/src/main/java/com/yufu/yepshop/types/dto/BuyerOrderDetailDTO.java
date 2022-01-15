package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/15 15:00
 */
@Getter
@Setter
public class BuyerOrderDetailDTO {
    private String sellerId;
    private String sellerType;
    private String sellerName;
    private String sellerAvatarUrl;

    @ApiModelProperty(value = "商品总价")
    private Integer totalFee;

    @ApiModelProperty(value = "实付款")
    private Integer payment;

    @ApiModelProperty(value = "订单状态")
    private OrderState orderState;

    private List<OrderItemDTO> items;

    @ApiModelProperty(value = "收货地址")
    private DeliveryAddressValue deliveryAddress;
}
