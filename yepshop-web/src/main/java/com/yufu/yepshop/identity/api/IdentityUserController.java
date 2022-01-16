package com.yufu.yepshop.identity.api;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.identity.service.YufuUserService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.command.BindLocationCommand;
import com.yufu.yepshop.types.command.BindMobileCommand;
import com.yufu.yepshop.types.dto.UserAccountDTO;
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

    public IdentityUserController(YufuUserService yufuUserService) {
        this.yufuUserService = yufuUserService;
    }

    @ApiOperation(value = "基本信息")
    @GetMapping("/me")
    public Result<UserAccountDTO> user() {
        return yufuUserService.user(currentUser().getId());
    }

    @ApiOperation(value = "绑定 - 用户手机号")
    @PutMapping("/bind-mobile")
    public Result<Boolean> bindMobile(@RequestBody BindMobileCommand command) {
        return yufuUserService.bindMobile(command);
    }

    @ApiOperation(value = "绑定 - 位置")
    @PutMapping("/bind-location")
    public Result<Boolean> bindLocation(@RequestBody BindLocationCommand command) {
        return yufuUserService.bindLocation(command);
    }
}
