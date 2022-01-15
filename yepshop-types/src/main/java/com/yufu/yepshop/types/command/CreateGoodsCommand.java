package com.yufu.yepshop.types.command;


import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author wang
 * @date 2021/12/26 20:39
 */
@Getter
@Setter
public class CreateGoodsCommand {

    @NotNull(message = "内容不能为空")
    private String text;

    @NotNull(message = "封面图片")
    private String picUrl;

    @NotNull(message = "图片不能为空")
    private String[] imageUrlList;

    @NotNull(message = "金额不能为空")
    private Integer price;

    private Integer originalPrice;

    private Integer postFee;

    private String categoryId;

    @NotNull(message = "发货地址不能为空")
    private RegionValue region;

    public String getTitleFromText() {
        int length = 64;
        if (text.length() > length) {
            return text.substring(0, length - 1);
        }
        return text;
    }
}
