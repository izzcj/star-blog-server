package com.ale.starblog.framework.core.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Oss配置
 *
 * @author Ale
 * @version 1.0.0 2026/1/30 16:04
 */
@Data
@ConfigurationProperties(prefix = "venus.oss")
public class OssProperties {

    /**
     * 默认提供器
     */
    private OssServiceProvider defaultProvider = OssServiceProvider.MINIO;

}
