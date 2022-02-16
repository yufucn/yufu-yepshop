package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.UserValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 22:55
 */
@Getter
@Setter
public class CommentDTO {

    private String goodsId;

    private String id;

    private Date creationTime;

    private String text;

    private UserValue user;

    private Integer totalReply;
}
