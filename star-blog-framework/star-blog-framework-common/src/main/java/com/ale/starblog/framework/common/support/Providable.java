package com.ale.starblog.framework.common.support;

/**
 * 可提供的
 *
 * @param <T> 提供类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 10:13
 */
public interface Providable<T> {

    /**
     * 提供
     *
     * @return 提供类型
     */
    T provide();
}
