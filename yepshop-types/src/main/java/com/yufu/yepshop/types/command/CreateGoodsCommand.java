package com.yufu.yepshop.types.command;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.value.RegionValue;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "详情介绍")
    @NotNull(message = "内容不能为空")
    private String text;

    @ApiModelProperty(value = "封面图片")
    @NotNull(message = "封面图片")
    private String picUrl;

    @ApiModelProperty(value = "图片Url列表")
    @NotNull(message = "图片不能为空")
    private String[] imageUrlList;

    @ApiModelProperty(value = "价格，单位分")
    @NotNull(message = "金额不能为空")
    private Integer price;

    @ApiModelProperty(value = "原始价格，单位分")
    private Integer originalPrice;

    @ApiModelProperty(value = "邮费，单位分")
    private Integer postFee;

    @ApiModelProperty(value = "学校Id，弹窗让用户选择一个学校，提交接口后，缓存在小程序")
    private String schoolId;

    @ApiModelProperty(value = "分类Id")
    private String categoryId;

    @ApiModelProperty(value = "成色Id")
    private String conditionId;

    @ApiModelProperty(value = "发货地")
    @NotNull(message = "发货地址不能为空")
    private RegionValue region;

    @ApiModelProperty(value = "商品状态：UP、DRAFT")
    private GoodsState goodsState;

    @JsonIgnore
    public String getTitleFromText() {
        int length = 64;
        if (text.length() > length) {
            return text.substring(0, length - 1);
        }
        return text;
    }
}
