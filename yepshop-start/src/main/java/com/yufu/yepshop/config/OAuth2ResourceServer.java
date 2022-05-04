package com.yufu.yepshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author wang
 * @date 2022/1/6 23:30
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("goods-api").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //只对 /api/* 应用 resource server 过滤
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
