package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/18 21:18
 */
@Getter
@Setter
public class UpdateOrderAddressCommand {
    @ApiModelProperty(value = "收货地址")
    private DeliveryAddressValue deliveryAddress;
}
