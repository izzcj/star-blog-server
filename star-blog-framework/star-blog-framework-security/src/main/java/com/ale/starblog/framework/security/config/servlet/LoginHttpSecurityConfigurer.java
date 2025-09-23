package com.ale.starblog.framework.security.config.servlet;

import com.ale.starblog.framework.security.authentication.VenusLogoutHandler;
import com.ale.starblog.framework.security.authentication.VenusLogoutSuccessHandler;
import com.ale.starblog.framework.security.contanst.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 登录配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@RequiredArgsConstructor
public class LoginHttpSecurityConfigurer implements HttpSecurityConfigurer {

    /**
     * 退出处理器
     */
    private final VenusLogoutHandler logoutHandler;

    /**
     * 退出成功处理器
     */
    private final VenusLogoutSuccessHandler logoutSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout(
            configurer -> configurer.logoutUrl(SecurityConstants.LOGOUT_URI)
                .invalidateHttpSession(false)
                .addLogoutHandler(this.logoutHandler)
                .logoutSuccessHandler(this.logoutSuccessHandler)
        );
    }

}
