package com.yufu.yepshop.identity.config;

import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author wang
 * @date 2022/1/9 16:43
 */
public class OauthJwtAccessTokenConverter extends JwtAccessTokenConverter {
    public OauthJwtAccessTokenConverter(YufuUserDetailsService userService) {
        super.setAccessTokenConverter(new OauthAccessTokenConverter(userService));
    }
}
