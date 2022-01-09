package com.yufu.yepshop.identity.controller;

import com.yufu.yepshop.identity.entity.UserAccountDO;
import com.yufu.yepshop.identity.oauth2.wechat.WechatAuthenticationToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wang
 * @date 2022/1/9 11:54
 */
@Api(tags = "授权 - 用户")
@RestController
public class OauthController {

    @ApiOperation(value = "基本信息")
    @GetMapping("/oauth/userinfo")
    public Map<String, String> user(Principal principal) {
        if (principal != null) {
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oauth2Authentication.getUserAuthentication();
            if (authentication instanceof WechatAuthenticationToken) {
                WechatAuthenticationToken t = (WechatAuthenticationToken) authentication;
                UserAccountDO userAccount = (UserAccountDO) t.getPrincipal();
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
        }
        return null;
    }
}
