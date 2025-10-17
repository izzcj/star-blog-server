package com.ale.starblog.framework.core.oss.support;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collection;

/**
 * 富文本对象上传Key替换器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:24
 */
@Component
public class RichTextOssUploadKeyReplacer extends RichTextOssUploadKeySupport implements OssUploadKeyReplacer {

    @Override
    public Object replaceKey(Field field, Object value, Collection<String> originalKeys, Collection<String> processedKeys) {
        if (String.class.isAssignableFrom(field.getType())) {
            if (!this.isRichTextField(field)) {
                return processedKeys.stream().findFirst().orElse(null);
            }

            var content = (String) value;
            if (StrUtil.isBlank(content)) {
                return null;
            }

            return doReplace(content, processedKeys);
        }

        return null;
    }

    /**
     * 替换富文本对象上传的key
     *
     * @param content       富文本内容
     * @param processedKeys 处理后的key集合
     * @return 替换后的富文本内容
     */
    private Object doReplace(String content, Collection<String> processedKeys) {
        for (String url : parseUrls(content)) {
            var name = FilenameUtils.getName(URI.create(url).getPath());
            for (String processedKey : processedKeys) {
                if (StringUtils.equals(name, FilenameUtils.getName(processedKey))) {
                    content = content.replace(url, url.replace(trimDomain(url), processedKey));
                    break;
                }
            }
        }

        return content;
    }
}
