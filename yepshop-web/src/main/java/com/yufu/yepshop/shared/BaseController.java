package com.yufu.yepshop.shared;

import com.yufu.yepshop.identity.entity.UserAccountDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.MappedSuperclass;

/**
 * @author wang
 * @date 2022/1/9 16:52
 */

public abstract class BaseController {
    protected static UserAccountDO user() {
        return (UserAccountDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
