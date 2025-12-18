package com.ale.starblog.framework.core.oss.support;

import com.ale.starblog.framework.common.utils.CastUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 集合对象上传key解析器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:20
 */
@Component
public class CollectionOssUploadKeyResolver implements OssUploadKeyResolver {

    @Override
    public Collection<String> resolveKeys(Field field, Object value) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            return CastUtils.cast(value);
        }

        return null;
    }

}
