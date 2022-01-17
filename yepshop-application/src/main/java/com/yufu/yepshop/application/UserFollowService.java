package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;

/**
 * @author wang
 * @date 2022/1/17 23:07
 */
public interface UserFollowService {
    Result<Boolean> followed(Long id);

    Result<Boolean> follow(Long id);

    Result<Boolean> unfollow(Long id);
}
