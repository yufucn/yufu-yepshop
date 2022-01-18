package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.enums.OrderRateResult;
import com.yufu.yepshop.types.enums.OrderRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * @author wang
 * @date 2022/1/18 22:43
 */
@Getter
@Setter
public class OrderRateCommand {

    @ApiModelProperty(value = "评价角色：SELLER,BUYER")
    private OrderRole role;

    @ApiModelProperty(value = "评价结果,可选值:GOOD(好评),NEUTRAL(中评),BAD(差评)")
    private OrderRateResult result;

    @ApiModelProperty(value = "评价内容")
    @Size(max = 500)
    private String content;

    @ApiModelProperty(value = "是否匿名,卖家评不能匿名。可选值:true(匿名),false(非匿名)")
    private Boolean anonymous;
}
