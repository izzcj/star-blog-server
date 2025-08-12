package com.ale.starblog.framework.common.enumeration;

/**
 * 枚举初始化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
public interface EnumInitializer {

    /**
     * 初始化枚举
     *
     * @param enumClass 枚举类
     */
    void initialize(Class<? extends BaseEnum<?>> enumClass);

}
