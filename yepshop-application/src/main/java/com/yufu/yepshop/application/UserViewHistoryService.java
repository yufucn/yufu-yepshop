package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;

/**
 * @author wang
 * @date 2022/1/16 11:40
 */
public interface UserViewHistoryService {
    Result<Boolean> view(Long id);
}
