package com.yufu.yepshop.identity.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Collection;
import java.util.Collections;

/**
 * @author wang
 * @date 2022/1/5 23:10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "yufu_user")
public class YufuUser implements UserDetails {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.yufu.yepshop.identity.entity.GenerateId")
    private Long id;

    private String userName;

    private String normalizedUserName;

    private String email;

    private String normalizedEmail;

    private Boolean emailConfirmed;

    private String password;

    private String mobile;

    private String mobileConfirmed;

    private Boolean twoFactorEnabled;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Integer accessFailedCount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var simpleGrantedAuthority = new SimpleGrantedAuthority("user");
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}