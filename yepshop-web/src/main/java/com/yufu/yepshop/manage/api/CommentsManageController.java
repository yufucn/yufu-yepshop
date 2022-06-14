package com.yufu.yepshop.manage.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.CommentDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.query.CommentQuery;
import com.yufu.yepshop.types.query.GoodsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @date 2022/5/17 21:31
 */
@Api(tags = "Shop - 管理 - 评论")
@RestController
@RequestMapping("/api/v1/manage/comments")
@Slf4j
public class CommentsManageController {

    private final GoodsService goodsService;

    public CommentsManageController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @ApiOperation(value = "搜索")
    @PostMapping("/search")
    public Result<Page<CommentDTO>> searchComments(
            @RequestBody CommentQuery query) {
        return goodsService.commentsGoods(query);
    }
}
