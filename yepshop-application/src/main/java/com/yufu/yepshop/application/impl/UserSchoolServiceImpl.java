package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.UserSchoolService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.persistence.dao.UserSchoolDAO;
import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.value.SchoolValue;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/20 0:25
 */
@Service
public class UserSchoolServiceImpl extends BaseService implements UserSchoolService {

    private final UserDomainService userDomainService;

    public UserSchoolServiceImpl(
            UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @Override
    public Result<Boolean> bind(BindSchoolCommand command) {
        userDomainService.bindSchool(command);
        return Result.success();
    }

    @Override
    public Result<List<SchoolValue>> schools(Long id) {
        return Result.success(userDomainService.schools(id));
    }
}
