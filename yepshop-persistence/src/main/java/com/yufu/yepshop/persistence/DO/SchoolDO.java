package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.value.AddressValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2022/1/7 20:25
 */
@Getter
@Setter
@Entity(name = "yufu_school")
@EntityListeners(AuditingEntityListener.class)
public class SchoolDO extends FullAuditedEntity {

    private String name;

    @Column(length = 512)
    private String abbreviation;

    @Embedded
    private AddressValue address;

    private Integer sortId;
}
