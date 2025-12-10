package com.ale.starblog.admin.common.cache;

import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.core.share.RedisSharedDataContext;
import com.ale.starblog.framework.core.share.SharedDataContextFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * 可缓存的热点数据管理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/9 16:23
 */
@Component
public class CacheableHotspotDataManager implements SmartLifecycle {

    /**
     * 缓存数据缓存Key
     */
    private static final String HOTSPOT_DATA_CACHE_KEY = "hotspotDataCache";

    /**
     * 热点数据初始化器
     */
    private final ObjectProvider<HotspotDataLoader<?>> cacheDataInitializers;

    /**
     * 翻译映射数据缓存上下文
     */
    private final RedisSharedDataContext hotspotDataCache;

    /**
     * phase
     */
    public static final int PHASE = SharedDataContextFactory.PHASE + 10;

    public CacheableHotspotDataManager(SharedDataContextFactory sharedObjectContextFactory, ObjectProvider<HotspotDataLoader<?>> cacheDataInitializers) {
        this.hotspotDataCache = sharedObjectContextFactory.createRedisSharedDataContext(HOTSPOT_DATA_CACHE_KEY);
        this.cacheDataInitializers = cacheDataInitializers;
        this.cacheDataInitializers.orderedStream().forEach(
            hotspotDataLoader -> this.hotspotDataCache.set(hotspotDataLoader.provide(), hotspotDataLoader.load())
        );
    }

    /**
     * 获取热点数据
     *
     * @param <T> 热点数据类型
     * @param key 缓存Key
     * @return 热点数据
     */
    public <T> T get(String key) {
        Object result = this.hotspotDataCache.get(key);
        if (result == null) {
            result = this.cacheDataInitializers.stream()
                .filter(hotspotDataLoader -> hotspotDataLoader.provide().equals(key))
                .findFirst()
                .map(HotspotDataLoader::load)
                .orElse(null);
            this.set(key, result);
        }
        return CastUtils.cast(result);
    }

    /**
     * 设置热点数据
     *
     * @param key 缓存Key
     * @param value 热点数据
     */
    public void set(String key, Object value) {
        this.hotspotDataCache.set(key, value);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        this.cacheDataInitializers.orderedStream().forEach(
            hotspotDataLoader -> hotspotDataLoader.persistenceHotspotData(
                this.hotspotDataCache.get(hotspotDataLoader.provide())
            )
        );
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public int getPhase() {
        return PHASE;
    }
}
