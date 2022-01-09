package com.yufu.yepshop.domain.types.auditing;

import lombok.Getter;
import lombok.Setter;

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
    private Date lastModificationTime;
    private Long lastModifierId;
}
