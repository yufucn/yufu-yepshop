package com.yufu.yepshop.identity.api;

import com.yufu.yepshop.application.UserSchoolService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.identity.service.YufuUserService;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.command.*;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/9 11:54
 */
@Api(tags = "身份 - 用户")
@RequestMapping("/api/v1/identity/user")
@RestController
public class IdentityUserController extends BaseController {

    private final YufuUserService yufuUserService;
    private final UserSchoolService userSchoolService;

    public IdentityUserController(
            YufuUserService yufuUserService,
            UserSchoolService userSchoolService) {
        this.yufuUserService = yufuUserService;
        this.userSchoolService = userSchoolService;
    }

    @ApiOperation(value = "绑定 - 用户手机号")
    @PutMapping("/bind-mobile")
    public Result<String> bindMobile(@RequestBody BindMobileCommand command) {
        return yufuUserService.bindMobile(command);
    }

    @ApiOperation(value = "绑定 - 位置")
    @PutMapping("/bind-location")
    public Result<Boolean> bindLocation(@RequestBody BindLocationCommand command) {
        return yufuUserService.bindLocation(command);
    }

    @ApiOperation(value = "绑定 - 学校")
    @PutMapping("/bind-school")
    public Result<Boolean> bindSchool(@RequestBody BindSchoolCommand command) {
        return userSchoolService.bind(command);
    }

    @ApiOperation(value = "设置 - 头像")
    @PutMapping("/set-avatar")
    public Result<Boolean> bindAvatarUrl(@RequestBody BindAvatarUrlCommand command) {
        return yufuUserService.bindAvatarUrl(command);
    }

    @ApiOperation(value = "设置 - 昵称")
    @PutMapping("/set-nickname")
    public Result<Boolean> bindAvatarUrl(@RequestBody BindNickNameCommand command) {
        return yufuUserService.bindNickName(command);
    }
}
