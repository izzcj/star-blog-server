package com.ale.starblog.framework.security.config.servlet;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Http安全配置器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
public interface HttpSecurityConfigurer {

    /**
     * 配置Http安全
     *
     * @param http http安全对象
     */
    void configure(HttpSecurity http) throws Exception;

}
