package com.ale.starblog.framework.common.security;

import java.lang.annotation.*;

/**
 * 权限分组
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/27
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionGroup {

    /**
     * 权限组标识
     *
     * @return 权限组标识
     */
    String value();
}
