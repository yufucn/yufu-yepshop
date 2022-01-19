package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.value.SchoolValue;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/20 0:22
 */
public interface UserSchoolService {

    Result<Boolean> bind(BindSchoolCommand command);

    Result<List<SchoolValue>> schools(Long id);

}
