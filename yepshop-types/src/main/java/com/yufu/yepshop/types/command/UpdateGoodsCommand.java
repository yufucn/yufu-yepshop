package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.CategoryValue;
import com.yufu.yepshop.types.value.RegionValue;
import io.swagger.annotations.ApiModelProperty;
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
public class UpdateGoodsCommand {

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

    @ApiModelProperty(value = "学校Id，弹窗让用户选择一个学校，提交接口后，缓存在小程序")
    private String schoolId;

    @ApiModelProperty(value = "分类Id")
    private String categoryId;

    @ApiModelProperty(value = "成色Id")
    private String conditionId;

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
