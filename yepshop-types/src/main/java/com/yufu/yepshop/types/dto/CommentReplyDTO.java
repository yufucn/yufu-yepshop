package com.yufu.yepshop.types.dto;

import java.util.Date;

/**
 * @author wang
 * @date 2021/12/26 23:03
 */
public class CommentReplyDTO {
    private String id;

    private String commentId;

    private String text;

    private UserAccountDTO user;

    private Date createTime;

    private String replyToUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserAccountDTO getUser() {
        return user;
    }

    public void setUser(UserAccountDTO user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
    }
}
