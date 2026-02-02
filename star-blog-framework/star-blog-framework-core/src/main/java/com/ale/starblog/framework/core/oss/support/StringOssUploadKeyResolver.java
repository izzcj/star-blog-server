package com.ale.starblog.framework.core.oss.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.ale.starblog.framework.core.oss.OssServiceProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 字符串对象上传key解析器
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:17
 */
@Component
public class StringOssUploadKeyResolver extends RichTextOssUploadKeySupport implements OssUploadKeyResolver {

    @Override
    public Collection<String> resolveKeys(Field field, Object value, OssServiceProvider ossServiceProvider) {
        if (String.class.isAssignableFrom(field.getType())) {
            var content = (String) value;
            if (StrUtil.isBlank(content)) {
                return null;
            }

            if (this.isRichTextField(field)) {
                return this.extractKeys(content, ossServiceProvider);
            }

            // 不处理外部链接
            if (HttpUtil.isHttp(content) || HttpUtil.isHttps(content)) {
                return Collections.emptyList();
            }
            return List.of(content);
        }

        return null;
    }

    /**
     * 提取富文本中的对象Key
     *
     * @param value 富文本内容
     * @param ossServiceProvider OSS实现
     * @return 对象Key集合
     */
    private Collection<String> extractKeys(String value, OssServiceProvider ossServiceProvider) {
        return this.parseUrls(value).stream()
            .map(url -> this.trimDomain(url, ossServiceProvider))
            .filter(StrUtil::isNotBlank)
            .toList();
    }
}
