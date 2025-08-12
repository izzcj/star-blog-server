package com.ale.starblog.framework.security.authorization.servlet;

import org.springframework.http.HttpMethod;

/**
 * 请求权限校验器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
public interface RequestPermissionChecker {

    /**
     * 检查请求是否允许
     *
     * @param method     请求方法
     * @param requestUri 请求路径
     * @return 是否允许
     */
    boolean check(HttpMethod method, String requestUri);

}
