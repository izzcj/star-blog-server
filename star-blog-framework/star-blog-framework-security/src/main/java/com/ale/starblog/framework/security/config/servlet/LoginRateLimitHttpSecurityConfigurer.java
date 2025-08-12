package com.ale.starblog.framework.security.config.servlet;

import com.ale.starblog.framework.security.filter.LoginRateLimitFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 登录频率限制配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
public class LoginRateLimitHttpSecurityConfigurer implements HttpSecurityConfigurer {

    /**
     * 配置Http安全
     *
     * @param http http安全对象
     */
    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(new LoginRateLimitFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
