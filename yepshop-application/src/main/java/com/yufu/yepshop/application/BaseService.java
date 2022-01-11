package com.yufu.yepshop.application;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wang
 * @date 2022/1/12 0:37
 */
public class BaseService {
    protected UserAccountDO currentUser() {
        return (UserAccountDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
