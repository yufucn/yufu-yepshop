package com.yufu.yepshop.identity.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.json.JSONObject;
import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.external.dto.WechatPhoneResponse;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.UserConvert;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.identity.service.YufuUserService;
import com.yufu.yepshop.types.command.BindLocationCommand;
import com.yufu.yepshop.types.command.BindMobileCommand;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.value.Location;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wang
 * @date 2022/1/5 23:08
 */
@Service
public class YufuUserServiceImpl extends BaseService implements YufuUserService {

    private final UserAccountDAO userAccountDAO;
    private final UserConvert userConvert = UserConvert.INSTANCE;
    private final WxMaService wxMaService;
    private static final int MAX_FAILED_COUNT = 5;

    public YufuUserServiceImpl(UserAccountDAO yufuUserRepository, WxMaService wxMaService) {
        this.userAccountDAO = yufuUserRepository;
        this.wxMaService = wxMaService;
    }

    @Override
    public void updateAccessFailedCountOrLock(String userName) {
        var userOptional = userAccountDAO.findByUserName(userName);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var count = user.getAccessFailedCount();
            if (count == null) {
                count = 0;
            }
            if (count + 1 >= MAX_FAILED_COUNT) {
                user.setAccountNonLocked(false);
            }
            user.setAccessFailedCount(count + 1);
            userAccountDAO.save(user);
        }
    }

    @Override
    public void resetAccessFailedCount(String userName) {
        var userOptional = userAccountDAO.findByUserName(userName);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            if (user.getAccessFailedCount() > 0) {
                user.setAccessFailedCount(0);
                userAccountDAO.save(user);
            }
        }
    }

    @Override
    public Result<UserAccountDTO> user(Long id) {
        UserAccountDO doo = userAccountDAO.findById(id).get();
        return Result.success(userConvert.toDTO(doo));
    }

    @Override
    public Result<String> bindMobile(BindMobileCommand command) {
        String token = "";
        try {
            token = wxMaService.getAccessToken();
        } catch (Exception exception) {

        }
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + token;
        JSONObject json = new JSONObject();
        json.put("code", command.getCode());
        RestTemplate restTemplate = new RestTemplate();
        WechatPhoneResponse response = restTemplate
                .postForObject(url, json, WechatPhoneResponse.class);
        if (response.getErrcode() == 0) {
            String phone = response.getPhone_info().getPhoneNumber();
            userAccountDAO.updateMobile(phone, currentUser().getId());
            return Result.success(phone, "成功");
        }
        return Result.fail("-1", response.getErrmsg());
    }

    @Override
    public Result<Boolean> bindLocation(BindLocationCommand command) {
        Location location = command.getLocation();
        userAccountDAO.updateLocation(location.getLatitude(), location.getLongitude(), currentUser().getId());
        return Result.success();
    }
}
