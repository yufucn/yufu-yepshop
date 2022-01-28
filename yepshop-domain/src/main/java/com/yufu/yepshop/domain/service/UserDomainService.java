package com.yufu.yepshop.domain.service;

import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import com.yufu.yepshop.types.value.SchoolValue;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/23 0:12
 */
public interface UserDomainService {

    Boolean bindSchool(Long schoolId);

    Boolean bindSchool(BindSchoolCommand command);

    UserDetailDTO userDetail(Long id);

    List<SchoolValue> schools(Long id);
}
