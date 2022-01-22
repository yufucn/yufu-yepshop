package com.yufu.yepshop.identity.config;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import lombok.var;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * UserDetailsService扩展
 *
 * @author wang
 * @date 2021/7/5 22:46
 */
@Service(value = "yufuUserDetailsService")
public class YufuUserDetailsService implements UserDetailsService {

    private final UserAccountDAO yufuUserRepository;
    private final UserDAO userDAO;

    public YufuUserDetailsService(UserAccountDAO yufuUserRepository, UserDAO userDAO) {
        this.yufuUserRepository = yufuUserRepository;
        this.userDAO = userDAO;
    }

    //http://localhost:5000/oauth/authorize?client_id=user-client&response_type=code&scope=all&redirect_uri=http://www.baidu.com
    @Override
    public UserAccountDO loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = yufuUserRepository.findByUserName(username);
        if (!optionalUser.isPresent()) {
            optionalUser = yufuUserRepository.findByEmail(username);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
        } else {
            return optionalUser.get();
        }
        return null;
    }

    public void update(UserAccountDO user) {
        yufuUserRepository.save(user);
    }

    public void register(UserAccountDO user) {
        user.setEnabled(true);
        user.setEmailConfirmed(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccessFailedCount(0);
        user.setNormalizedUserName(user.getUsername().toUpperCase(Locale.ROOT));
        if (user.getEmail() != null) {
            user.setNormalizedEmail(user.getEmail().toUpperCase(Locale.ROOT));
        }
        yufuUserRepository.save(user);

        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDAO.save(userDO);
    }
}
