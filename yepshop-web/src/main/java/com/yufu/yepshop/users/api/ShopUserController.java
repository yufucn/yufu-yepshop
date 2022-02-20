package com.yufu.yepshop.users.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.RequirementService;
import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/api/v1/shop/user")
@Slf4j
public class ShopUserController extends BaseController {

    private final UserService userService;
    private final GoodsService goodsService;
    private final RequirementService requirementService;

    public ShopUserController(
            UserService userService,
            GoodsService goodsService,
            RequirementService requirementService) {
        this.userService = userService;
        this.goodsService = goodsService;
        this.requirementService = requirementService;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<UserDetailDTO> getUser(@PathVariable Long id) {
        Result<Boolean> followed = userService.followed(id);
        Result<UserDetailDTO> result = userService.user(id);
        UserDetailDTO user = result.getData();
        user.getAccount().setFollowed(followed.getData());
        return result;
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

    @ApiOperation(value = "求购 - 列表（求购的、买到的、草稿）")
    @GetMapping("/{id}/requirements")
    public Result<Page<RequirementListDTO>> getUserRequirements(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage,
            @RequestParam String requirementState
    ) {
        return requirementService.pagedList(id, page, perPage, requirementState);
    }



    @ApiOperation(value = "关注列表")
    @GetMapping("/{id}/following")
    public Result<Page<UserDetailDTO>> getFollowing(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userService.following(id, page, perPage);
    }

    @ApiOperation(value = "粉丝列表")
    @GetMapping("/{id}/followers")
    public Result<Page<UserDetailDTO>> getFollowers(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userService.follwers(id, page, perPage);
    }

    @ApiOperation(value = "关注")
    @PostMapping("/follow/{id}")
    public Result<Boolean> follow(@PathVariable Long id) {
        return userService.follow(id);
    }

    @ApiOperation(value = "取消关注")
    @PostMapping("/unfollow/{id}")
    public Result<Boolean> unfollow(@PathVariable Long id) {
        return userService.unfollow(id);
    }

    @ApiOperation(value = "是否已关注")
    @GetMapping("/followed/{id}")
    public Result<Boolean> followed(@PathVariable Long id) {
        return userService.followed(id);
    }
}
