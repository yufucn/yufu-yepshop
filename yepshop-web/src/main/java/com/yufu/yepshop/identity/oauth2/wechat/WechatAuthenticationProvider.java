package com.yufu.yepshop.identity.oauth2.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import com.yufu.yepshop.identity.config.YufuUserDetailsService;
import com.yufu.yepshop.identity.entity.YufuUser;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.Locale;

/**
 * @author wang
 * @date 2022/1/5 0:46
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
//    private WxMaService wxMaService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();

//        WxMaJscode2SessionResult sessionInfo = null;
//        try {
//            sessionInfo = wxMaService.getUserService().getSessionInfo(code);
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        String openid = sessionInfo.getOpenid();
//        Result<MemberAuthDTO> memberAuthResult = memberFeignClient.loadUserByOpenId(openid);
//        // 微信用户不存在，注册成为新会员
//        if (memberAuthResult != null && ResultCode.USER_NOT_EXIST.getCode().equals(memberAuthResult.getCode())) {
//
//            String sessionKey = sessionInfo.getSessionKey();
//            String encryptedData = authenticationToken.getEncryptedData();
//            String iv = authenticationToken.getIv();
//            // 解密 encryptedData 获取用户信息
//            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
//
//            UmsMember member = new UmsMember();
//            BeanUtil.copyProperties(userInfo, member);
//            member.setOpenid(openid);
//            member.setStatus(GlobalConstants.STATUS_YES);
//            memberFeignClient.add(member);
//        }
        YufuUser user = new YufuUser();
        user.setUserName(code);
        user.setEnabled(true);
        user.setEmailConfirmed(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccessFailedCount(0);
        user.setNormalizedUserName(user.getUsername().toUpperCase(Locale.ROOT));
        user.setNormalizedEmail(user.getEmail().toUpperCase(Locale.ROOT));
        ((YufuUserDetailsService)userDetailsService).register(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(code);

        WechatAuthenticationToken result = new WechatAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

