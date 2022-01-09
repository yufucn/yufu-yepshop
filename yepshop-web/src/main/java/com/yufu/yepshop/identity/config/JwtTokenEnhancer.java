package com.yufu.yepshop.identity.config;

import com.yufu.yepshop.identity.entity.UserAccountDO;
import lombok.val;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * Jwt Token 扩展信息
 *
 * @author wang
 * @date  2021/7/13 22:30
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        UserAccountDO user = (UserAccountDO) oAuth2Authentication.getPrincipal();
        val info = new HashMap<String, Object>();
        info.put("nick_name", user.getNickName());
        info.put("user_id", user.getId().toString());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
