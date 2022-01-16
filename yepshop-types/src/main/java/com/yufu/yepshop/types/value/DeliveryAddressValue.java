package com.yufu.yepshop.types.value;

import com.yufu.yepshop.common.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author wang
 * @date 2022/1/8 10:40
 */
@Getter
@Setter
@Embeddable
@MappedSuperclass
public class DeliveryAddressValue extends AddressValue {

    @Column(length = 64)
    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @Column(length = Constants.MOBILE_LENGTH)
    @ApiModelProperty(value = "收货人手机号")
    private String mobile;
}
