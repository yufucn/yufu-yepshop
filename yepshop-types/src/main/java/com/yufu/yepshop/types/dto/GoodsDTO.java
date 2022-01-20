package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.GoodsValue;
import com.yufu.yepshop.types.value.RegionValue;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.Seller;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wang
 * @date 2022/1/9 18:33
 */
@Getter
@Setter
public class GoodsDTO {

    private GoodsValue goods;

    @ApiModelProperty(value = "分类Id")
    private String categoryId;

    @ApiModelProperty(value = "成色Id")
    private String conditionId;

    @ApiModelProperty(value = "原始价格，单位分")
    private Integer originalPrice;

    @ApiModelProperty(value = "价格，单位分")
    private Integer price;

    @ApiModelProperty(value = "邮费，单位分")
    private Integer postFee;

    private RegionValue region;

    private Seller seller;

    private GoodsState goodsState;

    private AuditState auditState;

    private String[] imageUrlList;

    @ApiModelProperty(value = "详细描述")
    private String text;

    @ApiModelProperty(value = "评论数")
    private Integer totalComment;

    @ApiModelProperty(value = "收藏数")
    private Integer totalCollect;

    @ApiModelProperty(value = "创建时间")
    private Date creationTime;

    @ApiModelProperty(value = "学校")
    private SchoolValue school;
}
