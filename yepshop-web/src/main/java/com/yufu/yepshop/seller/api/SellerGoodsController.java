package com.yufu.yepshop.seller.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/6 21:15
 */
@Api(tags = "Shop - 卖家 - 商品")
@RestController
@RequestMapping("/api/v1/shop/seller/goods")
@Slf4j
public class SellerGoodsController extends BaseController {

    private final GoodsService goodsService;

    public SellerGoodsController(GoodsService goodsService) {

        this.goodsService = goodsService;
    }

    @ApiOperation(value = "列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsState", value = "ALL、UP,DOWN,SOLD,DRAFT", paramType = "query", dataType = "String"),
    })
    @GetMapping()
    public Result<Page<GoodsListDTO>> getGoods(
            @RequestParam Integer page,
            @RequestParam Integer perPage,
            @RequestParam String goodsState
    ) {
        return goodsService.pagedList(currentUser().getId(), page, perPage, goodsState);
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public Result<String> createGoods(
            @RequestBody CreateGoodsCommand command) {
        return goodsService.create(command);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/{id}")
    public Result<Boolean> updateGoods(
            @PathVariable Long id,
            @RequestBody UpdateGoodsCommand command) {
        return goodsService.update(id, command);
    }

    @ApiOperation(value = "设置(上架、下架、草稿)")
    @PutMapping("/{id}/update-state")
    public Result<Boolean> updateState(
            @PathVariable Long id,
            @RequestParam GoodsState state) {
        return goodsService.update(id, state);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteGoods(@PathVariable Long id) {
        return goodsService.delete(id);
    }

}
