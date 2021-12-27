package com.yufu.yepshop.persistence;

import java.util.Date;

/**
 * @author wang
 * @date 2021/12/28 0:08
 */
public class ItemCommentReplyDO {
    private Long id;

    private Long commentId;

    private String text;

    private Date creationTime;

    private Long creatorId;

    private Long replyToUserId;
}
