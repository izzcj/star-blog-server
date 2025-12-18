package com.ale.starblog.framework.core.oss.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

/**
 * 集合上传对象Key替换器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:23
 */
@Component
public class CollectionOssUploadKeyReplacer implements OssUploadKeyReplacer {

    @Override
    public Object replaceKey(Field field, Object value, Collection<String> originalKeys, Collection<String> processedKeys) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            if (Set.class.isAssignableFrom(field.getType())) {
                return Sets.newHashSet(processedKeys);
            } else {
                return Lists.newArrayList(processedKeys);
            }
        }

        return null;
    }
}
