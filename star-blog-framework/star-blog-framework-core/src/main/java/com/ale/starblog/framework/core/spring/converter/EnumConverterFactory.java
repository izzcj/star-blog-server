package com.ale.starblog.framework.core.spring.converter;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 枚举转换工厂
 *
 * @author Ale
 * @version 1.0.0 2025/12/12 9:51
 */
@Component
@SuppressWarnings({"rawtypes"})
public class EnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @NonNull
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new EnumConverter<>(targetType);
    }

    /**
     * 枚举转换器
     *
     * @param <T> 枚举类型
     */
    @RequiredArgsConstructor
    private static final class EnumConverter<T extends BaseEnum> implements Converter<String, T> {
        /**
         * 枚举类型
         */
        private final Class<T> enumClass;

        @Nonnull
        @Override
        public T convert(@Nonnull String source) {
            return BaseEnum.getByValue(this.enumClass, source);
        }
    }
}
