package com.yufu.yepshop.identity.config;

import com.yufu.yepshop.identity.entity.YufuUser;
import com.yufu.yepshop.identity.repository.YufuUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * UserDetailsService扩展
 *
 * @author wang
 * @date 2021/7/5 22:46
 */
@Service(value = "yufuUserDetailsService")
@RequiredArgsConstructor
public class YufuUserDetailsService implements UserDetailsService {

    private final YufuUserRepository yufuUserRepository;
//    private final PasswordEncoder passwordEncoder;

    //http://localhost:5000/oauth/authorize?client_id=user-client&response_type=code&scope=all&redirect_uri=http://www.baidu.com
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = yufuUserRepository.findByUserName(username);
        if(!optionalUser.isPresent()){
            optionalUser = yufuUserRepository.findByEmail(username);
            if(optionalUser.isPresent()){
                return optionalUser.get();
            }
        } else {
            return optionalUser.get();
        }
        return null;
    }

    public void register(YufuUser user) {
        user.setPassword("a");
        user.setEnabled(true);
        user.setEmailConfirmed(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccessFailedCount(0);
        user.setNormalizedUserName(user.getUsername().toUpperCase(Locale.ROOT));
        user.setNormalizedEmail(user.getEmail().toUpperCase(Locale.ROOT));
        yufuUserRepository.save(user);
    }
}
