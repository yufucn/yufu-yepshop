package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.PostFeeType;
import com.yufu.yepshop.types.value.GoodsValue;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.Seller;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wang
 * @date 2022/1/9 22:16
 */
@Getter
@Setter
public class GoodsListDTO {

    private SchoolValue school;

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

    @ApiModelProperty(value = "FREE(卖家承担运费),COLLECT(到付)")
    private PostFeeType postFeeType;

    private Seller seller;

    private GoodsState goodsState;

    private AuditState auditState;

    private Integer totalComment;

    private Integer totalCollect;

    private Date viewDate;

    private Date creationTime;
}
