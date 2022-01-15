package com.yufu.yepshop.seller.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import io.swagger.annotations.Api;
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
public class SellerGoodsController {

    private final GoodsService goodsService;

    public SellerGoodsController(GoodsService goodsService) {

        this.goodsService = goodsService;
    }

    @ApiOperation(value = "列表")
    @GetMapping()
    public Result<Page<GoodsListDTO>> getGoods(
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage,
            @RequestParam(value = "ALL(所有)、UP(上架)、DOWN(下架)、SOLD(卖出)、DRAFT(草稿)") String state
    ) {
        return goodsService.pagedList(page, perPage, state);
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public Result<Boolean> createGoods(@RequestBody CreateGoodsCommand command) {
        return goodsService.create(command);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/{id}")
    public Result<Boolean> updateGoods(
            @PathVariable String id,
            @RequestBody UpdateGoodsCommand command) {
        return goodsService.update(id, command);
    }

    @ApiOperation(value = "设置(上架、下架、卖出、草稿)")
    @PutMapping("/{id}/update-state")
    public Result<Boolean> updateState(
            @PathVariable String id,
            @RequestParam GoodsState goodsState) {
        return goodsService.update(id, goodsState);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteGoods(@PathVariable String id) {
        return goodsService.delete(id);
    }

}
