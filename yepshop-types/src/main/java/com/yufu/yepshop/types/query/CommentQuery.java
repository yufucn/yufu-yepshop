package com.yufu.yepshop.types.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/5/25 0:20
 */
@Setter
@Getter
public class CommentQuery {
    private String goodsId;
    private Integer page = 0;
    private Integer perPage = 10;
    private String keyword;
    private String auditState;
}
