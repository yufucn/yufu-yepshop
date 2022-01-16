package com.yufu.yepshop.types.value;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author wang
 * @date 2022/1/16 22:47
 */
@Setter
@Getter
@Embeddable
@MappedSuperclass
public class Location {

    @ApiModelProperty(value = "纬度，范围为 -90~90，负数表示南纬")
    private Double latitude;

    @ApiModelProperty(value = "经度，范围为 -180~180，负数表示西经")
    private Double longitude;
}
