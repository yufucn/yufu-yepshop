package com.yufu.yepshop.goods.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.UserCollectService;
import com.yufu.yepshop.application.GoodsViewService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.query.GoodsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final UserCollectService userCollectService;
    private final GoodsViewService viewHistoryService;

    public GoodsController(
            GoodsService goodsService,
            UserCollectService userCollectService,
            GoodsViewService viewHistoryService) {
        this.goodsService = goodsService;
        this.userCollectService = userCollectService;
        this.viewHistoryService = viewHistoryService;
    }

    @ApiOperation(value = "搜索")
    @PostMapping("/search")
    public Result<Page<GoodsListDTO>> searchGoods(
            @RequestBody GoodsQuery query) {
        return goodsService.search(query);
    }

    @ApiOperation(value = "收藏")
    @PostMapping("/{id}/collect")
    public Result<Boolean> collectGoods(@PathVariable Long id) {
        return userCollectService.collect(id);
    }

    @ApiOperation(value = "取消收藏")
    @PostMapping("/{id}/cancel-collect")
    public Result<Boolean> cancelCollect(@PathVariable Long id) {
        return userCollectService.cancelCollect(id);
    }

    @ApiOperation(value = "是否已收藏")
    @GetMapping("/{id}/collected")
    public Result<Boolean> collected(@PathVariable Long id) {
        return userCollectService.collected(id);
    }

    @ApiOperation(value = "浏览")
    @PostMapping("/{id}/view")
    public Result<Boolean> viewGoods(@PathVariable Long id) {
        return viewHistoryService.view(id);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<GoodsDTO> getGoods(@PathVariable Long id) {
        return goodsService.get(id);
    }


//    @ApiOperation(value = "评论")
//    @PostMapping("/{id}/comments")
//    public Result<Boolean> createGoodsComment(
//            @PathVariable String id,
//            @RequestBody CreateGoodsCommentCommand command) {
//        return null;
//    }
//
//    @ApiOperation(value = "评论 - 列表")
//    @GetMapping("/{id}/comments")
//    public Result<Boolean> commentsGoods(
//            @PathVariable String id,
//            @RequestParam Integer page,
//            @RequestParam(name = "per_page") Integer perPage
//    ) {
//        return null;
//    }
//
//    @ApiOperation(value = "评论 - 回复")
//    @PostMapping("/{id}/comment/{commentId}/reply")
//    public Result<Boolean> commentReplyGoods(
//            @PathVariable String id,
//            @PathVariable String commentId,
//            @RequestBody CreateGoodsCommentReplyCommand command) {
//        return null;
//    }
}
