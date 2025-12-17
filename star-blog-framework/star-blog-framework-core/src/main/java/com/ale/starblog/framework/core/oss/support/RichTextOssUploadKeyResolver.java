package com.ale.starblog.framework.core.oss.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * 富文本对象上传key解析器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:17
 */
@Component
public class RichTextOssUploadKeyResolver extends RichTextOssUploadKeySupport implements OssUploadKeyResolver {

    @Override
    public Collection<String> resolveKeys(Field field, Object value) {
        if (String.class.isAssignableFrom(field.getType())) {
            var content = (String) value;
            if (StrUtil.isBlank(content)) {
                return null;
            }

            return this.isRichTextField(field) ? extractKeys(content) : List.of((String) value);
        }

        return null;
    }

    /**
     * 提取富文本中的对象Key
     *
     * @param value 富文本内容
     * @return 对象Key集合
     */
    private Collection<String> extractKeys(String value) {
        return this.parseUrls(value).stream()
            .map(this::trimDomain)
            .filter(StrUtil::isNotBlank)
            .toList();
    }
}
