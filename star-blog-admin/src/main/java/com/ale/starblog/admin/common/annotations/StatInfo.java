package com.ale.starblog.admin.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 统计信息注解
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 11:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatInfo {

    /**
     * 类型
     *
     * @return 类型
     */
    String type();
}
