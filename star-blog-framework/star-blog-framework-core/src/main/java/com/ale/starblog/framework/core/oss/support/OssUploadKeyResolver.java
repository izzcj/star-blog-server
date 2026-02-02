package com.ale.starblog.framework.core.oss.support;

import com.ale.starblog.framework.core.oss.OssServiceProvider;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 对象上传key解析器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:12
 */
public interface OssUploadKeyResolver {

    /**
     * 解析对象上传的key
     *
     * @param field              字段
     * @param value              字段值
     * @param ossServiceProvider OSS实现
     * @return key集合
     */
    Collection<String> resolveKeys(Field field, Object value, OssServiceProvider ossServiceProvider);

}
