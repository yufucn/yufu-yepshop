package com.yufu.yepshop.types.value;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wang
 * @date 2021/12/26 21:17
 */
@Getter
@Setter
@Embeddable
@MappedSuperclass
public class RegionValue implements Serializable {
    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    @Column(length = 128)
    private String country;

    @ApiModelProperty(value = "国家编码 默认中国：0")
    @Column(length = 10)
    private String countryCode;

    /**
     * 省份
     */
    @Column(length = 128)
    @ApiModelProperty(value = "省份")
    private String province;

    @Column(length = 10)
    @ApiModelProperty(value = "省份编码")
    private String provinceCode;

    /**
     * 城市
     */
    @Column(length = 128)
    @ApiModelProperty(value = "城市")
    private String city;

    @Column(length = 10)
    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    @Column(length = 128)
    private String area;

    @Column(length = 10)
    @ApiModelProperty(value = "地区编码")
    private String areaCode;
}
