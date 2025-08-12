package com.ale.starblog.framework.common.security;

import java.lang.annotation.*;

/**
 * 权限检查注解
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/27
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    /**
     * 权限标识
     *
     * @return 权限标识
     */
    String value();

}
