package com.yufu.yepshop.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wang
 * @date 2022/5/19 0:03
 */
@Controller
public class LogoutController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @RequestMapping("/oauth/logout")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        // token can be revoked here if needed
        String token = request.getParameter("token");
        System.out.println(token);
        if (token != null) {
            consumerTokenServices.revokeToken(token);
        }
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
