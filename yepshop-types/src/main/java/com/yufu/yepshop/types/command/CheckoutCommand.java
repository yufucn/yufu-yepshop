package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/13 21:40
 */
@Getter
@Setter
public class CheckoutCommand {
    @ApiModelProperty(value = "按照不同卖家，创建不同的订单，订单数量限制1-10")
    @Size(min = 1, max = 10)
    List<CreateOrderCommand> orders;

    @ApiModelProperty(value = "付款方式")
    private PayType payType;

    @ApiModelProperty(value = "卖家收货地址")
    private DeliveryAddressValue deliveryAddress;
}
