package com.yufu.yepshop.domain.types.auditing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/7 20:48
 */
@Getter
@Setter
@MappedSuperclass
public class AuditedEntity extends CreationAuditedEntity {

    @LastModifiedDate
    private Date lastModificationTime;

    @LastModifiedBy
    private Long lastModifierId;
}
