package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import com.yufu.yepshop.types.dto.UserListDTO;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/17 23:07
 */
public interface UserFollowService {
    Result<Boolean> followed(Long id);

    Result<Boolean> follow(Long id);

    Result<Boolean> unfollow(Long id);

    Result<Page<UserDetailDTO>> following(Long userId, Integer page, Integer perPage);

    Result<Page<UserDetailDTO>> follwers(Long userId,Integer page, Integer perPage);
}
