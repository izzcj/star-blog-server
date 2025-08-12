package com.ale.starblog.framework.common.redis;

import cn.hutool.core.util.ArrayUtil;
import com.ale.starblog.framework.common.utils.KryoUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.NonNull;

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

    /**
     * Serialize the given object to binary data.
     *
     * @param o object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return EMPTY_ARRAY;
        }
        return KryoUtils.serialize(o);
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }

        return KryoUtils.deserialize(bytes);
    }
}
