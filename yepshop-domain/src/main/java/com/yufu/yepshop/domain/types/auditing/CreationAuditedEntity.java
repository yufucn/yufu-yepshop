package com.yufu.yepshop.domain.types.auditing;

import com.yufu.yepshop.domain.types.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
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
    @CreatedDate
    @Column(updatable = false)
    private Date creationTime;

    @CreatedBy
    @Column(updatable = false)
    private Long creatorId;
}
