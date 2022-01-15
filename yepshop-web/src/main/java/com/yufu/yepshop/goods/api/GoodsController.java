package com.yufu.yepshop.goods.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.application.impl.GoodsServiceImpl;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommentCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentReplyCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/15 22:01
 */
@Api(tags = "Shop - 商品")
@RestController
@RequestMapping("/api/v1/shop/goods")
@Slf4j
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "收藏")
    @PostMapping("/{id}/collect")
    public Result<Boolean> collectGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<GoodsDTO> getGoods(@PathVariable String id) {
        return goodsService.get(id);
    }


    @ApiOperation(value = "评论")
    @PostMapping("/{id}/comments")
    public Result<Boolean> createGoodsComment(
            @PathVariable String id,
            @RequestBody CreateGoodsCommentCommand command) {
        return null;
    }

    @ApiOperation(value = "评论 - 列表")
    @GetMapping("/{id}/comments")
    public Result<Boolean> commentsGoods(
            @PathVariable String id,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
        return null;
    }

    @ApiOperation(value = "评论 - 回复")
    @PostMapping("/{id}/comment/{commentId}/reply")
    public Result<Boolean> commentReplyGoods(
            @PathVariable String id,
            @PathVariable String commentId,
            @RequestBody CreateGoodsCommentReplyCommand command) {
        return null;
    }
}
