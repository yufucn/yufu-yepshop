package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.UserDetailDTO;

/**
 * @author wang
 * @date 2022/1/23 0:44
 */
public interface UserService {
    Result<UserDetailDTO> user(Long id);
}
