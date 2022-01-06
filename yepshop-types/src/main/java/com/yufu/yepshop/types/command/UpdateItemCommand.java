package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.Category;
import com.yufu.yepshop.types.value.Money;
import com.yufu.yepshop.types.value.Region;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 21:58
 */
@Getter
@Setter
public class UpdateItemCommand {

    @NotNull(message = "内容不能为空")
    private String text;

    @NotNull(message = "图片不能为空")
    private List<String> urls;

    @NotNull(message = "金额不能为空")
    private Money price;

    private Money originalPrice;

    private Money postage;

    private List<Category> categories;

    @NotNull(message = "发货地址不能为空")
    private Region shippingAddress;
}
