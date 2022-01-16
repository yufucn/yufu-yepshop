package com.yufu.yepshop.types.value;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * @author wang
 * @date 2021/12/26 18:49
 */

@Getter
@Setter
@Embeddable
@MappedSuperclass
public class AddressValue extends RegionValue {
    /**
     * 街道地址
     */
    @ApiModelProperty(value = "详细地址（到门牌号码）")
    @Column(length = 512)
    private String street;
}
