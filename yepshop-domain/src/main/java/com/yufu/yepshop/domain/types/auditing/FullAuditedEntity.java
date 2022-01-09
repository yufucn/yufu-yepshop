package com.yufu.yepshop.domain.types.auditing;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/7 20:49
 */
@Getter
@Setter
@MappedSuperclass
public class FullAuditedEntity extends AuditedEntity {
    private Boolean isDeleted;
    private Long deleterId;
    private Date deletionTime;
}
