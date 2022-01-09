package com.yufu.yepshop.identity.entity;

import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.value.RegionValue;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * @author wang
 * @date 2022/1/5 23:10
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "yufu_user_account")
public class UserAccountDO extends FullAuditedEntity implements UserDetails {

    @Column(length = 128)
    private String userName;

    @Column(length = 128)
    private String nickName;

    @Column(length = 8)
    private String language;

    @Column(length = 8)
    private String gender;

    @Column(length = 128)
    private String normalizedUserName;

    private String email;

    private String normalizedEmail;

    private Boolean emailConfirmed;

    @Column(length = 128)
    private String password;

    @Column(length = Constants.MOBILE_LENGTH)
    private String mobile;

    private String mobileConfirmed;

    private Boolean twoFactorEnabled;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Integer accessFailedCount;

    private String avatarUrl;

    @Column(length = 64)
    private String openId;

    @Column(length = 64)
    private String unionId;

    @Embedded
    private RegionValue region;

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