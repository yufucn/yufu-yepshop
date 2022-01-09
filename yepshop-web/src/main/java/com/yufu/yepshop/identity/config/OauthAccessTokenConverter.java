package com.yufu.yepshop.identity.config;

import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

/**
 * @author wang
 * @date 2022/1/9 16:44
 */
public class OauthAccessTokenConverter extends DefaultAccessTokenConverter {
    public OauthAccessTokenConverter(YufuUserDetailsService userService) {
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userService);
        super.setUserTokenConverter(converter);
    }
}
