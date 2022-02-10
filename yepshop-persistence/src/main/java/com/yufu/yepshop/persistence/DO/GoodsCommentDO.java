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
@Entity(name = "yufu_goods_comment")
@Table(indexes = {
        @Index(columnList = "goodsId")
})
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class GoodsCommentDO extends FullAuditedEntity {
    private Long goodsId;

    private String text;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalReply;

    @Enumerated(EnumType.STRING)
    private AuditState auditState;
}
