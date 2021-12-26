package com.yufu.yepshop.types.dto;

import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 22:55
 */
public class CommentDTO {

    private String itemId;

    private String id;

    private UserDTO user;

    private Date createTime;

    private String text;

    private List<CommentReplyDTO> replies;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CommentReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentReplyDTO> replies) {
        this.replies = replies;
    }
}
