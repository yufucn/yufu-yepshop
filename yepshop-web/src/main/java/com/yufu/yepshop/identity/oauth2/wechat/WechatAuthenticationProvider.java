package com.yufu.yepshop.identity.oauth2.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * @author wang
 * @date 2022/1/5 0:46
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private WxMaService wxMaService;

    /**
     * 微信认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
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
        String openid = sessionInfo.getOpenid();
        Result<MemberAuthDTO> memberAuthResult = memberFeignClient.loadUserByOpenId(openid);
        // 微信用户不存在，注册成为新会员
        if (memberAuthResult != null && ResultCode.USER_NOT_EXIST.getCode().equals(memberAuthResult.getCode())) {

            String sessionKey = sessionInfo.getSessionKey();
            String encryptedData = authenticationToken.getEncryptedData();
            String iv = authenticationToken.getIv();
            // 解密 encryptedData 获取用户信息
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

            UmsMember member = new UmsMember();
            BeanUtil.copyProperties(userInfo, member);
            member.setOpenid(openid);
            member.setStatus(GlobalConstants.STATUS_YES);
            memberFeignClient.add(member);
        }
        UserDetails userDetails = ((MemberUserDetailsServiceImpl) userDetailsService).loadUserByOpenId(openid);
        WechatAuthenticationToken result = new WechatAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

