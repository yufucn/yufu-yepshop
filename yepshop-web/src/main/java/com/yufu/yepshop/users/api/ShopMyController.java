package com.yufu.yepshop.users.api;

import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.dto.UserDTO;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ShopMyController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "详情")
    @GetMapping("/")
    public Result<UserDetailDTO> getUser() {
        Long id = currentUser().getId();
        return userService.user(id);
    }
}
