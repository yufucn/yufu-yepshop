package com.yufu.yepshop.goods;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
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
@Api(tags = "Shop - 闲置")
@RestController
@RequestMapping("/api/v1/goods")
@Slf4j
public class GoodsController {

    private GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

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
    public Result<Boolean> createGoods(@RequestBody CreateGoodsCommand command) {
        return goodsService.create(command);
    }

    @ApiOperation(value = "更新闲置")
    @PutMapping("/{id}")
    public Result<Boolean> updateGoods(
            @PathVariable String id,
            @RequestBody UpdateGoodsCommand command) {
        return goodsService.update(id, command);
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
            @RequestBody CreateGoodsCommentCommand command) {
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
            @RequestBody CreateGoodsCommentReplyCommand command) {
        return null;
    }
}
