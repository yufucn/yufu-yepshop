package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.Category;
import com.yufu.yepshop.types.value.Money;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 22:15
 */
@Getter
@Setter
@ApiModel("闲置明细DTO")
public class ItemDTO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("详情")
    private String text;

    @ApiModelProperty("图片地址集合")
    private List<String> urls;

    @ApiModelProperty("价格")
    private Money price;

    @ApiModelProperty("原价")
    private Money originalPrice;

    @ApiModelProperty("运费")
    private Money postage;

    @ApiModelProperty("分类")
    private List<Category> categories;

    @ApiModelProperty("评论总数")
    private Long totalComment;

    @ApiModelProperty("收藏总数")
    private Long totalCollect;
}
