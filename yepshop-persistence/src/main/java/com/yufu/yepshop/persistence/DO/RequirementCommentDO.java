package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.AuditState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2021/12/28 0:08
 */
@Getter
@Setter
@Entity(name = "yufu_requirement_comment")
@Table(indexes = {
        @Index(columnList = "requirementId")
})
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class RequirementCommentDO extends FullAuditedEntity {
    private Long requirementId;

    private String text;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalReply;

    @Enumerated(EnumType.STRING)
    private AuditState auditState;
}
