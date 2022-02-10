package com.yufu.yepshop.users.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/23 0:07
 */
@Api(tags = "Shop - 我的")
@RestController
@RequestMapping("/api/v1/shop/my")
@Slf4j
public class ShopMyController extends BaseController {

    private final UserService userService;
    private final GoodsService goodsService;

    public ShopMyController(
            UserService userService,
            GoodsService goodsService) {
        this.userService = userService;
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/")
    public Result<UserDetailDTO> getUser() {
        Long id = currentUser().getId();
        return userService.user(id);
    }

    @ApiOperation(value = "收藏")
    @GetMapping("/collection")
    public Result<Page<GoodsListDTO>> getUserGoods(
            @RequestParam Integer page,
            @RequestParam Integer perPage
    ) {
        return goodsService.collectionList(page, perPage);
    }

    @ApiOperation(value = "浏览")
    @GetMapping("/view")
    public Result<Page<GoodsListDTO>> view(
            @RequestParam Integer page,
            @RequestParam Integer perPage
    ) {
        return goodsService.viewList(page, perPage);
    }

    @ApiOperation(value = "清除浏览")
    @GetMapping("/view-clear")
    public Result<Boolean> viewClear() {
        return goodsService.viewClear();
    }

    @ApiOperation(value = "关注")
    @GetMapping("/following")
    public Result<Page<UserDetailDTO>> getFollowing(
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userService.following(currentUser().getId(), page, perPage);
    }

    @ApiOperation(value = "粉丝")
    @GetMapping("/followers")
    public Result<Page<UserDetailDTO>> getFollowers(
            @RequestParam Integer page,
            @RequestParam Integer perPage) {
        return userService.follwers(currentUser().getId(), page, perPage);
    }
}
