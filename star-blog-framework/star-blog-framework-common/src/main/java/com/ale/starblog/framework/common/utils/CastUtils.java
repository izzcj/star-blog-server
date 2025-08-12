package com.ale.starblog.framework.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 类型转换工具类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/25
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CastUtils {

    /**
     * 转换类型
     *
     * @param value 值
     * @return 特定类型的值
     * @param <T> 类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object value) {
        return (T) value;
    }

}
