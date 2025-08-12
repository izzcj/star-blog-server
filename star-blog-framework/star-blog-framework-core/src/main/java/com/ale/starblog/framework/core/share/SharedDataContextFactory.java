package com.ale.starblog.framework.core.share;

import com.ale.starblog.framework.common.constants.StringConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * 共享数据上下文工厂
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 12:00
 */
@Slf4j
@RequiredArgsConstructor
public class SharedDataContextFactory implements SmartLifecycle {

    /**
     * RedisTemplate
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 是否在启动或停止时清除共享数据
     */
    private final boolean clearOnLifecycle;

    /**
     * 创建Redis共享数据上下文
     *
     * @param sharedContextKey 共享上下文Key
     * @return Redis共享数据上下文
     */
    public RedisSharedDataContext createRedisSharedDataContext(String sharedContextKey) {
        return new RedisSharedDataContext(this.redisTemplate, sharedContextKey);
    }

    /**
     * 清除共享数据
     */
    private void clearSharedObjects() {
        if (!clearOnLifecycle) {
            return;
        }

        Set<String> keys = this.redisTemplate.keys(RedisSharedDataContext.SHARED_OBJECT_CONTEXT_KEY_PREFIX + StringConstants.ASTERISK);
        if (keys != null && !keys.isEmpty()) {
            log.info("清除共享数据: {}", keys);
            this.redisTemplate.delete(keys);
        }
    }

    @Override
    public void start() {
        clearSharedObjects();
    }

    @Override
    public void stop() {
        clearSharedObjects();
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
