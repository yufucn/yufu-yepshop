package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.UserValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wang
 * @date 2021/12/26 23:03
 */
@Getter
@Setter
public class CommentReplyDTO {
    private String id;

    private String commentId;

    private String text;

    private Date creationTime;

    private UserValue user;

    private UserValue toUser;
}
