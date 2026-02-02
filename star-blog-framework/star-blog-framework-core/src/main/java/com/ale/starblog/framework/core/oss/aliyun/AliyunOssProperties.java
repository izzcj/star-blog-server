package com.ale.starblog.framework.core.oss.aliyun;

import com.ale.starblog.framework.common.support.EnableAwareProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云OSS配置
 *
 * @author Ale
 * @version 1.0.0 2026/1/29 10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "venus.oss.aliyun")
public class AliyunOssProperties extends EnableAwareProperties {

    /**
     * 端点URL
     */
    private String endpoint;

    /**
     * 区域
     */
    private String region;

    /**
     * 访问域名
     */
    private String domain;

    /**
     * 访问Key
     */
    private String accessKeyId;

    /**
     * 密钥Key
     */
    private String accessKeySecret;

    /**
     * 存储桶
     */
    private String bucket;
}