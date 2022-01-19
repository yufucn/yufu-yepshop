package com.yufu.yepshop.types.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/19 22:50
 */
@Getter
@Setter
public class UserDTO {

    @ApiModelProperty(value = "全部")
    private Integer totalGoods;//UP + Sold
    @ApiModelProperty(value = "在售/我发布的")
    private Integer totalUp;
    @ApiModelProperty(value = "已售/我卖出的")
    private Integer totalSold;

    @ApiModelProperty(value = "我买到的")
    private Integer totalBuy;

    @ApiModelProperty(value = "粉丝")
    private Integer followers;
    @ApiModelProperty(value = "关注")
    private Integer following;
    @ApiModelProperty(value = "历史浏览")
    private Integer totalView;
    @ApiModelProperty(value = "收藏")
    private Integer totalCollect;
}
