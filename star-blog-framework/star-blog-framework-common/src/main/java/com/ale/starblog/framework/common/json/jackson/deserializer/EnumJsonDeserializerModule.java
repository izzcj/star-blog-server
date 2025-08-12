package com.ale.starblog.framework.common.json.jackson.deserializer;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.enumeration.EnumInitializer;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

/**
 * 枚举反序列化模块
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@Component
@SuppressWarnings({"unchecked"})
public class EnumJsonDeserializerModule extends SimpleModule implements EnumInitializer {

    @Override
    public String getModuleName() {
        return "EnumJsonDeserializerModule";
    }

    @Override
    public void initialize(Class<? extends BaseEnum<?>> enumClass) {
        this.addDeserializer(CastUtils.cast(enumClass), new EnumJsonDeserializer(enumClass));
    }
}
