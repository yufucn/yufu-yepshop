package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.RequirementState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2022/2/15 23:11
 */
@Getter
@Setter
@Entity(name = "yufu_requirements")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class RequirementDO extends FullAuditedEntity {
    private Long schoolId;

    @Column(length = 255)
    private String title;

    private Long categoryId;

    private Long conditionId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalCollect;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AuditState auditState;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private RequirementState requirementState;
}
