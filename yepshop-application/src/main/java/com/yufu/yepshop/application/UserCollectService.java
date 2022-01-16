package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.RegionDTO;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 10:51
 */
public interface UserCollectService {
    Result<Boolean> collect(Long id);
}
