package com.yufu.yepshop.users.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.UserFollowService;
import com.yufu.yepshop.application.UserSchoolService;
import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.identity.service.YufuUserService;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/7 1:02
 */
@Api(tags = "Shop - 用户")
@RestController
@RequestMapping("/api/v1/shop/my")
@Slf4j
public class ShopUserController extends BaseController {

    private final UserService userService;
    private final GoodsService goodsService;
    private final UserFollowService userFollowService;

    public ShopUserController(
                              UserService userService,
                              GoodsService goodsService,
                              UserFollowService userFollowService) {
        this.userService = userService;
        this.goodsService = goodsService;
        this.userFollowService = userFollowService;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<UserDetailDTO> getUser(@PathVariable Long id) {
        return userService.user(id);
    }


    @ApiOperation(value = "商品 - 列表（上架的、下架的、卖出的、草稿）")
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
    public Result<Page<UserAccountDTO>> getFollowing(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userFollowService.following(id, page, perPage);
    }

    @ApiOperation(value = "粉丝列表")
    @GetMapping("/{id}/followers")
    public Result<Page<UserAccountDTO>> getFollowers(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userFollowService.follwers(id, page, perPage);
    }

    @ApiOperation(value = "关注")
    @PostMapping("/follow/{id}")
    public Result<Boolean> follow(@PathVariable Long id) {
        return userFollowService.follow(id);
    }

    @ApiOperation(value = "取消关注")
    @PostMapping("/unfollow/{id}")
    public Result<Boolean> unfollow(@PathVariable Long id) {
        return userFollowService.unfollow(id);
    }

    @ApiOperation(value = "是否已关注")
    @GetMapping("/followed/{id}")
    public Result<Boolean> followed(@PathVariable Long id) {
        return userFollowService.followed(id);
    }
}
