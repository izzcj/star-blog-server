package com.ale.starblog.framework.core.oss.support;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 对象上传Key替换器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:22
 */
public interface OssUploadKeyReplacer {

    /**
     * 替换对象上传的key
     *
     * @param field         字段
     * @param value         字段值
     * @param originalKeys  原始key集合
     * @param processedKeys 处理后的key集合
     * @return 替换后的值
     */
    Object replaceKey(Field field, Object value, Collection<String> originalKeys, Collection<String> processedKeys);

}
