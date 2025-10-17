package com.ale.starblog.framework.core.oss.support;

import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.core.oss.RichText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本对象上传key支持
 *
 * @author Ale
 * @version 1.0.0 2025/10/17 16:13
 */
abstract class RichTextOssUploadKeySupport {

    /**
     * Regex
     */
    private static final Pattern FILE_URL_PATTERN = Pattern.compile("(src|href)\\s*=\\s*([\"'\\\\]+(?=https?://)([^'\"\\\\]+)[\"'\\\\]+)", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

    /**
     * Minio bucket
     */
    @Value("${venus.oss.minio.bucket}")
    private String bucket;

    /**
     * 解析URL
     *
     * @param value 值
     * @return 解析后的URL集合
     */
    protected Set<String> parseUrls(String value) {
        Set<String> urls = new HashSet<>();
        Matcher matcher = FILE_URL_PATTERN.matcher(value);
        while (matcher.find()) {
            urls.add(matcher.group(matcher.groupCount()));
        }

        return urls;
    }

    /**
     * 移除域名
     *
     * @param url URL
     * @return 移除域名后的URL
     */
    protected String trimDomain(String url) {
        return StringUtils.substringAfter(URI.create(url).getPath(), StringConstants.SLASH + bucket + StringConstants.SLASH);
    }

    /**
     * 是否为富文本字段
     *
     * @param field 字段
     * @return true-是 false-否
     */
    protected boolean isRichTextField(Field field) {
        return AnnotatedElementUtils.isAnnotated(field, RichText.class);
    }
}
