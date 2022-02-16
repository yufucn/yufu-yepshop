package com.yufu.yepshop.types.command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2021/12/26 23:14
 */
@Getter
@Setter
public class CreateCommentReplyCommand {

    private String text;

    private String replyToUserId;
}
