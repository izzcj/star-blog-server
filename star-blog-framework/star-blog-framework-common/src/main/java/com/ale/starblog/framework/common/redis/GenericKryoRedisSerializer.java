package com.ale.starblog.framework.common.redis;

import cn.hutool.core.util.ArrayUtil;
import com.ale.starblog.framework.common.utils.KryoUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Kryo序列化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/8/12
 **/
public class GenericKryoRedisSerializer implements RedisSerializer<Object> {

    /**
     * 空字节
     */
    static final byte[] EMPTY_ARRAY = new byte[0];

    /**
     * 是否可以序列化
     *
     * @param type 类型
     * @return bool
     */
    @Override
    public boolean canSerialize(@NonNull Class<?> type) {
        return true;
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        if (o == null) {
            return EMPTY_ARRAY;
        }
        return KryoUtils.serialize(o);
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }

        return KryoUtils.deserialize(bytes);
    }
}
