package com.yufu.yepshop.types.value;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * @author wang
 * @date 2022/1/15 15:43
 */
@Getter
@Setter
@Embeddable
@MappedSuperclass
public class GoodsValue {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标题")
    private String skuId;

    @ApiModelProperty(value = "图片url地址")
    private String picUrl;

    @ApiModelProperty(value = "价格，单位分")
    private Integer price;
}
