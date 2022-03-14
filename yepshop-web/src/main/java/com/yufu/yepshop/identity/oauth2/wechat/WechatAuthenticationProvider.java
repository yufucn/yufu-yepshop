package com.yufu.yepshop.identity.oauth2.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.yufu.yepshop.identity.config.YufuUserDetailsService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.types.value.RegionValue;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashSet;

/**
 * @author wang
 * @date 2022/1/5 0:46
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private final YufuUserDetailsService userDetailsService;
    private final WxMaService wxMaService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();

        WxMaJscode2SessionResult sessionInfo = null;
        try {
            sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String unionId = sessionInfo.getUnionid();
        String openId = sessionInfo.getOpenid();
        UserAccountDO user = userDetailsService.loadUserByUsername(openId);
        String sessionKey = sessionInfo.getSessionKey();
        String encryptedData = authenticationToken.getEncryptedData();
        String iv = authenticationToken.getIv();
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        if (user == null) {
            user = new UserAccountDO();
            user.setUnionId(unionId);
            user.setOpenId(openId);
            user.setUserName(openId);
            user.setAvatarUrl(userInfo.getAvatarUrl());
            user.setGender(userInfo.getGender());
            user.setNickName(userInfo.getNickName());
            user.setLanguage(userInfo.getLanguage());
            RegionValue regionValue = new RegionValue();
            regionValue.setArea("");
            regionValue.setProvince(userInfo.getProvince());
            regionValue.setCity(userInfo.getCity());
            regionValue.setCountry(userInfo.getCountry());
            user.setRegion(regionValue);
            userDetailsService.register(user);
        } else {
            user.setGender(userInfo.getGender());
            user.setLanguage(userInfo.getLanguage());
            RegionValue regionValue = new RegionValue();
            regionValue.setArea("");
            regionValue.setProvince(userInfo.getProvince());
            regionValue.setCity(userInfo.getCity());
            regionValue.setCountry(userInfo.getCountry());
            user.setRegion(regionValue);
            userDetailsService.update(user);
        }

        WechatAuthenticationToken result = new WechatAuthenticationToken(user, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

