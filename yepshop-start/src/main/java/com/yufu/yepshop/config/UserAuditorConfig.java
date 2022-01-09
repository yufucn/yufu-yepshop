package com.yufu.yepshop.config;

import com.yufu.yepshop.identity.entity.UserAccountDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/9 20:08
 */
@Configuration
@Slf4j
public class UserAuditorConfig implements AuditorAware<Long> {

    /**
     * @return 创建人 or 修改人
     */
    @Override
    public Optional<Long> getCurrentAuditor() {
        UserAccountDO user;
        try {
            user = (UserAccountDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.ofNullable(user.getId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}


