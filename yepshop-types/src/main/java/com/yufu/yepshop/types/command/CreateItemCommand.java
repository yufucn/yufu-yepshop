package com.yufu.yepshop.types.command;


import com.yufu.yepshop.types.value.CategoryValue;
import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 20:39
 */
@Getter
@Setter
public class CreateItemCommand {

    @NotNull(message = "内容不能为空")
    private String text;

    @NotNull(message = "封面图片")
    private String picUrl;

    @NotNull(message = "图片不能为空")
    private List<String> urls;

    @NotNull(message = "金额不能为空")
    private BigDecimal price;

    private BigDecimal originalPrice;

    private BigDecimal postFee;

    private String categoryId;

    @NotNull(message = "发货地址不能为空")
    private RegionValue shippingAddress;
}
