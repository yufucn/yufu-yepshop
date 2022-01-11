package com.yufu.yepshop.shared;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wang
 * @date 2022/1/9 16:52
 */

public abstract class BaseController {
    protected UserAccountDO currentUser() {
        return (UserAccountDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
