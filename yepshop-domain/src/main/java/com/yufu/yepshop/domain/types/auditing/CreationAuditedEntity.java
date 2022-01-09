package com.yufu.yepshop.domain.types.auditing;

import com.yufu.yepshop.domain.types.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/7 20:46
 */
@Getter
@Setter
@MappedSuperclass
public class CreationAuditedEntity extends BaseEntity {
    private Date creationTime;
    private Long creatorId;
}
