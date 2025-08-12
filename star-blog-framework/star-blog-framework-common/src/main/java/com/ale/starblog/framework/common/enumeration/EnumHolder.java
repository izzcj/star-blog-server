package com.ale.starblog.framework.common.enumeration;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.common.support.Comment;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.common.utils.ClassUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 枚举持有器
 *
 * @author Ale
 * @version 1.0.0 2025/6/3 16:48
 */
@Component
@NoArgsConstructor
public final class EnumHolder implements EnumInitializer {

    /**
     * 枚举选项
     */
    private static final List<Option> ENUM_OPTIONS = Lists.newArrayList();

    /**
     * 枚举值映射
     */
    private static final Map<String, Map<Object, String>> ENUM_VALUE_MAPPING = Maps.newHashMap();

    /**
     * 读写锁
     */
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();


    @Override
    public void initialize(Class<? extends BaseEnum<?>> enumClass) {
        this.rwLock.writeLock().lock();

        try {
            ENUM_OPTIONS.add(
                Option.of(
                    Optional.ofNullable(AnnotationUtils.findAnnotation(enumClass, Comment.class))
                        .map(comment -> String.format("%s(%s)", comment.value(), enumClass.getSimpleName()))
                        .orElse(enumClass.getSimpleName()),
                    enumClass.getName(),
                    enumClass.getName()
                )
            );

            BaseEnum<?>[] enumConstants = enumClass.getEnumConstants();
            Map<Object, String> enumValueMap = Maps.newHashMapWithExpectedSize(enumConstants.length);
            for (BaseEnum<?> enumConstant : enumConstants) {
                enumValueMap.put(enumConstant.getValue(), enumConstant.getName());
            }
            ENUM_VALUE_MAPPING.put(enumClass.getName(), enumValueMap);
        } finally {
            this.rwLock.writeLock().unlock();
        }
    }

    /**
     * 获取枚举选项
     *
     * @return 枚举选项
     */
    public static List<Option> getEnumOptions() {
        return ENUM_OPTIONS;
    }

    /**
     * 获取枚举值映射
     *
     * @param enumClassName 枚举类
     * @return 枚举项
     */
    public static Map<Object, String> getEnumValueMapping(String enumClassName) {
        Map<Object, String> result = ENUM_VALUE_MAPPING.get(enumClassName);
        if (CollectionUtil.isEmpty(result)) {
            Class<?> enumClass = ClassUtils.loadClass(enumClassName);
            if (enumClass.isEnum() && BaseEnum.class.isAssignableFrom(enumClass)) {
                Object[] enumConstants = enumClass.getEnumConstants();
                Map<Object, String> enumValueMap = Maps.newHashMapWithExpectedSize(enumConstants.length);
                for (Object enumConstant : enumConstants) {
                    BaseEnum<?> baseEnum = (BaseEnum<?>) enumConstant;
                    enumValueMap.put(baseEnum.getValue(), baseEnum.getName());
                }
                ENUM_VALUE_MAPPING.put(enumClassName, enumValueMap);
            }
        }
        return result;
    }

}
