package com.ale.starblog.framework.common.json.jackson.deserializer;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.exception.JsonDeserializerException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * 枚举反序列化器
 *
 * @param <T> 枚举值类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
public class EnumJsonDeserializer<T> extends StdDeserializer<BaseEnum<T>> {

    /**
     * 枚举类
     */
    private final Class<? extends BaseEnum<T>> clazz;

    protected EnumJsonDeserializer(Class<? extends BaseEnum<T>> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    @Override
    public BaseEnum<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        T value = CastUtils.cast(p.getValueAsString());
        BaseEnum<T> baseEnum = BaseEnum.getByValue(this.clazz, value);
        if (baseEnum == null) {
            throw new JsonDeserializerException("不正确的枚举[{}]值：{}", this.clazz.getSimpleName(), value);
        }

        return baseEnum;
    }
}
