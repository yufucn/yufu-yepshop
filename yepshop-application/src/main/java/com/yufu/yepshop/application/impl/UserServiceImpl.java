package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import org.springframework.stereotype.Service;

/**
 * @author wang
 * @date 2022/1/23 0:46
 */
@Service
public class UserServiceImpl  extends BaseService implements UserService {

    private final UserDomainService domainService;

    public UserServiceImpl(UserDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public Result<UserDetailDTO> user(Long id) {
        return Result.success(domainService.userDetail(id));
    }
}
