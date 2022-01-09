package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.AuditedEntity;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2021/12/27 23:36
 */
@Getter
@Setter
@Entity(name = "yufu_user_address")
@EntityListeners(AuditingEntityListener.class)
public class UserAddressDO extends AuditedEntity {

    private Long userId;

    @Embedded
    private DeliveryAddressValue deliveryAddress;

    private Integer isDefault;
}
