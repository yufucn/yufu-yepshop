package com.yufu.yepshop.identity.api;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.shared.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wang
 * @date 2022/1/9 11:54
 */
@Api(tags = "身份 - 用户")
@RestController
public class OauthController extends BaseController {

    @ApiOperation(value = "基本信息")
    @GetMapping("/oauth/userinfo")
    public Map<String, String> user(Principal principal) {
        if (principal != null) {
            UserAccountDO userAccount = currentUser();
            Map<String, String> map = new LinkedHashMap<>();
            map.put("user_id", userAccount.getId().toString());
            map.put("email", userAccount.getEmail());
            map.put("user_name", userAccount.getUsername());
            map.put("nick_name", userAccount.getNickName());
            map.put("mobile", userAccount.getMobile());
            map.put("gender", userAccount.getGender());
            map.put("language", userAccount.getLanguage());
            map.put("avatar_url", userAccount.getAvatarUrl());
            return map;
        }
        return null;
    }
}
