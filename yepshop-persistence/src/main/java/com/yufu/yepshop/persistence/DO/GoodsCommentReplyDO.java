package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.AuditState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author wang
 * @date 2021/12/28 0:08
 */
@Getter
@Setter
@Entity(name = "yufu_goods_comment_reply")
public class GoodsCommentReplyDO extends FullAuditedEntity {
    private Long goodsId;

    private Long commentId;

    private String text;

    private Long replyToUserId;

    @Enumerated(EnumType.STRING)
    private AuditState auditState;
}
