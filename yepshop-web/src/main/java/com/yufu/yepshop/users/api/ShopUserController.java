package com.yufu.yepshop.users.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.identity.service.YufuUserService;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.dto.UserListDTO;
import com.yufu.yepshop.types.enums.OrderState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/7 1:02
 */
@Api(tags = "Shop - 用户")
@RestController
@RequestMapping("/api/v1/shop/users")
@Slf4j
public class ShopUserController extends BaseController {

    private final YufuUserService yufuUserService;
    private final GoodsService goodsService;

    public ShopUserController(YufuUserService yufuUserService,
                              GoodsService goodsService) {
        this.yufuUserService = yufuUserService;
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<UserAccountDTO> getUser(@PathVariable Long id) {
        return yufuUserService.user(id);
    }


    @ApiOperation(value = "闲置 - 列表（上架的、下架的、卖出的、草稿）")
    @GetMapping("/{id}/goods")
    public Result<Page<GoodsListDTO>> getUserGoods(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage,
            @RequestParam String goodsState
    ) {
        return goodsService.pagedList(id, page, perPage, goodsState);
    }


    @ApiOperation(value = "关注列表")
    @GetMapping("/{id}/following")
    public Result<Page<UserListDTO>> getFollowing(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return null;
    }

    @ApiOperation(value = "粉丝列表")
    @GetMapping("/{id}/followers")
    public Result<Page<UserListDTO>> getFollowers(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return null;
    }

    @ApiOperation(value = "关注")
    @PostMapping("/follow/{id}")
    public Result<Boolean> follow(@PathVariable Long id) {
        return null;
    }


//    @ApiOperation(value = "订单 - 列表")
//    @GetMapping("/{id}/orders")
//    public Result<Page<GoodsListDTO>> getUserOrders(
//            @PathVariable String id,
//            @RequestParam Integer page,
//            @RequestParam Integer perPage,
//            @RequestParam OrderState orderState
//    ) {
//        return null;
//    }
}
