package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.AuditedEntity;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * @author wang
 * @date 2021/12/27 23:36
 */
@Getter
@Setter
@Entity(name = "yufu_user_address")
public class UserAddressDO extends AuditedEntity {

    private Long userId;

    @Embedded
    private DeliveryAddressValue deliveryAddress;

    private Integer isDefault;
}
