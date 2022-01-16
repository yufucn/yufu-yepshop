package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateSchoolCommand;
import com.yufu.yepshop.types.dto.RegionDTO;
import com.yufu.yepshop.types.dto.SchoolDTO;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 21:50
 */

public interface SchoolService {
    Result<List<SchoolDTO>> getAll();

    Result<Boolean> create(CreateSchoolCommand command);
}
