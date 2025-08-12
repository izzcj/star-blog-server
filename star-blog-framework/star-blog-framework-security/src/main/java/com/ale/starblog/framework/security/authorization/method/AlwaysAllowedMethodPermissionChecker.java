package com.ale.starblog.framework.security.authorization.method;

import java.lang.reflect.Method;

/**
 * 不进行权限检查
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/27
 **/
public class AlwaysAllowedMethodPermissionChecker implements MethodPermissionChecker {

    @Override
    public boolean check(Class<?> targetClass, Object target, Method method, String permission) {
        return true;
    }
}
