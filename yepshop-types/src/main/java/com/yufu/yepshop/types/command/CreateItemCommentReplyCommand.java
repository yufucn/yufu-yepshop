package com.yufu.yepshop.types.command;

/**
 * @author wang
 * @date 2021/12/26 23:14
 */
public class CreateItemCommentReplyCommand {

    private String commentId;

    private String text;

    private String replyToUserId;


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

    public String getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId;
    }
}
