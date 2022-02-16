package com.yufu.yepshop.requirement.api;

import com.yufu.yepshop.application.RequirementService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.*;
import com.yufu.yepshop.types.dto.*;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.RequirementState;
import com.yufu.yepshop.types.query.GoodsQuery;
import com.yufu.yepshop.types.query.RequirementQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/2/15 23:58
 */
@Api(tags = "Shop - 求购")
@RestController
@RequestMapping("/api/v1/shop/requirements")
@Slf4j
public class RequirementController {

    private final RequirementService requirementService;

    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public Result<String> create(
            @RequestBody CreateRequirementCommand command) {
        return requirementService.create(command);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/{id}")
    public Result<Boolean> update(
            @PathVariable Long id,
            @RequestBody CreateRequirementCommand command) {
        return requirementService.update(id, command);
    }

    @ApiOperation(value = "设置(求购、已购、草稿)")
    @PutMapping("/{id}/update-state")
    public Result<Boolean> updateState(
            @PathVariable Long id,
            @RequestParam RequirementState state) {
        return requirementService.update(id, state);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return requirementService.delete(id);
    }



    @ApiOperation(value = "跑马灯")
    @PostMapping("/tip")
    public Result<Page<RequirementListDTO>> tip(
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
        return requirementService.tip(page, perPage);
    }

    @ApiOperation(value = "搜索")
    @PostMapping("/search")
    public Result<Page<RequirementListDTO>> search(
            @RequestBody RequirementQuery query) {
        return requirementService.search(query);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<RequirementListDTO> get(@PathVariable Long id) {
        return requirementService.get(id);
    }

    @ApiOperation(value = "评论")
    @PostMapping("/{id}/comments")
    public Result<String> createGoodsComment(
            @PathVariable Long id,
            @RequestBody CreateCommentCommand command) {
        return requirementService.comment(id, command);
    }

    @ApiOperation(value = "评论 - 列表")
    @GetMapping("/{id}/comments")
    public Result<Page<RequirementCommentDTO>> commentsGoods(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage
    ) {
        return requirementService.comments(id, page, perPage);
    }

    @ApiOperation(value = "评论 - 删除")
    @DeleteMapping("/{id}/comment/{commentId}")
    public Result<Boolean> deleteComment(
            @PathVariable Long id,
            @PathVariable Long commentId) {
        return requirementService.commentDelete(id, commentId);
    }

    @ApiOperation(value = "评论 - 回复")
    @PostMapping("/{id}/comment/{commentId}/reply")
    public Result<String> commentReplyGoods(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @RequestBody CreateCommentReplyCommand command) {
        return requirementService.commentReply(id, commentId, command);
    }


    @ApiOperation(value = "评论 - 回复 - 列表")
    @GetMapping("/{id}/comment/{commentId}/replies")
    public Result<Page<CommentReplyDTO>> commentReplyGoodsList(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage) {
        return requirementService.commentReplyList(id, commentId, page, perPage);
    }

    @ApiOperation(value = "评论 - 回复 - 删除")
    @DeleteMapping("/{id}/comment/{commentId}/reply/{replyId}")
    public Result<Boolean> commentReplyGoodsDelete(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @PathVariable Long replyId) {
        return requirementService.commentReplyDelete(id, commentId, replyId);
    }
}
