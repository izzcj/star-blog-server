package com.ale.starblog.admin.common.cache;

import com.ale.starblog.framework.common.support.Providable;

/**
 * 热点数据加载器
 *
 * @param <T> 缓存数据类型
 * @author Ale
 * @version 1.0.0 2025/12/9 14:25
 */
public interface HotspotDataLoader<T> extends Providable<String> {

    /**
     * 加载热点数据
     *
     * @return 热点数据
     */
    T load();

    /**
     * 持久化热点数据
     *
     * @param cacheData 缓存数据
     */
    default void persistenceHotspotData(T cacheData) {
        // 默认不持久化
    }

}
