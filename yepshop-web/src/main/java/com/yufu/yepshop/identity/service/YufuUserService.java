package com.yufu.yepshop.identity.service;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.BindLocationCommand;
import com.yufu.yepshop.types.command.BindMobileCommand;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.dto.UserDetailDTO;

/**
 * @author wang
 * @date 2022/1/5 23:08
 */
public interface YufuUserService {
    void updateAccessFailedCountOrLock(String userName);

    void resetAccessFailedCount(String userName);

    Result<UserAccountDTO> user(Long id);

    Result<UserDetailDTO> userDetail(Long id);

    Result<String> bindMobile(BindMobileCommand command);

    Result<Boolean> bindLocation(BindLocationCommand command);
}
