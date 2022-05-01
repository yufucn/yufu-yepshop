package com.yufu.yepshop.mall;

import com.yufu.yepshop.dto.*;
import com.yufu.yepshop.identity.config.YufuUserDetailsService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @date 2022/3/3 21:14
 */
@Controller
public class PersonalController {

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private YufuUserDetailsService yufuUserDetailsService;

    @GetMapping({"/login-user", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @PostMapping("/login-user")
    @ResponseBody
    public ResultT login(@RequestParam("loginName") String loginName,
                         @RequestParam("verifyCode") String verifyCode,
                         @RequestParam("password") String password,
                         HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        UserAccountDO user = userAccountDAO.findByMobile(loginName);

        String loginResult = "success";
        if (user == null) {
            loginResult = "手机号不存在！";
            return ResultGenerator.genFailResult(loginResult);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            loginResult = "手机号或密码错误！";
            return ResultGenerator.genFailResult(loginResult);
        }
        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            MallUserVO vo = new MallUserVO();
            vo.setUserId(user.getId());
            vo.setNickName(user.getUsername());
            vo.setLoginName(user.getUsername());
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, vo);
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult("登录失败！");
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultT register(@RequestParam("loginName") String loginName,
                            @RequestParam("verifyCode") String verifyCode,
                            @RequestParam("password") String password,
                            HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String registerResult = "";
        UserAccountDO user = userAccountDAO.findByMobile(loginName);
        if (user != null) {
            registerResult = "手机号已存在！";
            return ResultGenerator.genFailResult(registerResult);
        }
        user = new UserAccountDO();
        user.setUserName(loginName);
        user.setMobile(loginName);
        user.setPassword(passwordEncoder.encode(password));
        yufuUserDetailsService.register(user);
        registerResult = "success";
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }
}
