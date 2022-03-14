package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.enums.PostFeeType;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.AddressValue;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/11 23:07
 */
@Getter
@Setter
public class CreateOrderCommand {

    @ApiModelProperty(value = "商品列表")
    private List<CreateOrderItemCommand> items;

    @ApiModelProperty(value = "商品总价格")
    private Integer totalFee;

    @ApiModelProperty(value = "实付款(totalFee + postFee) 可手动修改")
    private Integer payment;

    @ApiModelProperty(value = "邮费")
    private Integer postFee;

    private PostFeeType postFeeType;

    @ApiModelProperty(value = "卖家Id")
    private String sellerId;

    @ApiModelProperty(value = "卖家类型")
    private SellerType sellerType;
}
