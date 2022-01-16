package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/15 22:44
 */
@Getter
@Setter
public class UserAddressDTO {

    @ApiModelProperty(value = "收货地址")
    private DeliveryAddressValue deliveryAddress;

    @ApiModelProperty(value = "是否默认地址")
    private Boolean isDefault;
}
