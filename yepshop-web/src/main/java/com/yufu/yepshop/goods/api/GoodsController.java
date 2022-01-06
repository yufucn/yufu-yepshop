package com.yufu.yepshop.goods.api;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateItemCommand;
import com.yufu.yepshop.types.command.CreateItemCommentCommand;
import com.yufu.yepshop.types.command.CreateItemCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateItemCommand;
import com.yufu.yepshop.types.dto.CommentDTO;
import com.yufu.yepshop.types.dto.ItemDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/6 21:15
 */
@Api(tags = "校闲 - 闲置")
@RestController
@RequestMapping("/api/v1/goods")
@Slf4j
public class GoodsController {

    @ApiOperation(value = "闲置 - 列表")
    @GetMapping()
    public Result<Page<ItemDTO>> getGoods(
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
        return null;
    }

    @ApiOperation(value = "新增闲置")
    @PostMapping
    public Result<Boolean> createGoods(@RequestBody CreateItemCommand command) {
        return null;
    }

    @ApiOperation(value = "更新闲置")
    @PutMapping("/{id}")
    public Result<Boolean> updateGoods(
            @PathVariable String id,
            @RequestBody UpdateItemCommand command) {
        return null;
    }

    @ApiOperation(value = "闲置上架")
    @PutMapping("/{id}/up-shelf")
    public Result<Boolean> upshelfGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "闲置下架")
    @PutMapping("/{id}/down-shelf")
    public Result<Boolean> downshelfGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "闲置收藏")
    @PostMapping("/{id}/collect")
    public Result<Boolean> collectGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "闲置详情")
    @GetMapping("/{id}")
    public Result<ItemDTO> getGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "闲置删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteGoods(@PathVariable String id) {
        return null;
    }

    @ApiOperation(value = "闲置评论")
    @PostMapping("/{id}/comments")
    public Result<Boolean> createGoodsComment(
            @PathVariable String id,
            @RequestBody CreateItemCommentCommand command) {
        return null;
    }

    @ApiOperation(value = "闲置评论 - 列表")
    @GetMapping("/{id}/comments")
    public Result<Boolean> commentsGoods(
            @PathVariable String id,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
        return null;
    }

    @ApiOperation(value = "闲置评论 - 回复")
    @PostMapping("/{id}/comment/{commentId}/reply")
    public Result<Boolean> commentReplyGoods(
            @PathVariable String id,
            @PathVariable String commentId,
            @RequestBody CreateItemCommentReplyCommand command) {
        return null;
    }
}
