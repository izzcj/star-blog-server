package com.ale.starblog.framework.core.oss;

import com.ale.starblog.framework.core.oss.minio.MinioOssService;
import com.ale.starblog.framework.core.oss.minio.MinioProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * OSS对象存储自动配置
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/12/5 星期一 18:21
 */
@AutoConfiguration
@Import(OssSupport.class)
@EnableConfigurationProperties(MinioProperties.class)
public class OssAutoConfiguration {

    /**
     * OSS对象存储服务获取基URL端点处理器Bean
     *
     * @param ossServices OSS服务实现
     * @return 端点处理器Bean
     */
    @Bean
    public OssServiceBaseUrlEndpoint ossServiceBaseUrlEndpoint(ObjectProvider<OssService> ossServices) {
        return new OssServiceBaseUrlEndpoint(ossServices);
    }

    /**
     * OSS对象存储服务临时对象文件移除端点处理器Bean
     *
     * @param ossServices OSS服务实现
     * @return 端点处理器Bean
     */
    @Bean
    public OssServiceTempObjectRemoveEndpoint ossServiceTempObjectRemovalEndpoint(ObjectProvider<OssService> ossServices) {
        return new OssServiceTempObjectRemoveEndpoint(ossServices);
    }

    /**
     * OSS对象存储服务上传端点处理器Bean
     *
     * @param ossServices OSS服务实现
     * @return 端点处理器Bean
     */
    @Bean
    public OssServiceUploadEndpoint ossServiceUploadEndpoint(ObjectProvider<OssService> ossServices) {
        return new OssServiceUploadEndpoint(ossServices);
    }

    /**
     * OSS对象存储服务下载端点处理器Bean
     *
     * @param ossServices OSS服务实现
     * @return 端点处理器Bean
     */
    @Bean
    public OssServiceDownloadEndpoint ossServiceDownloadEndpoint(ObjectProvider<OssService> ossServices) {
        return new OssServiceDownloadEndpoint(ossServices);
    }

    /**
     * Minio对象存储服务Bean
     *
     * @param minioProperties 配置属性
     * @return Minio服务Bean
     * @throws Exception 异常
     */
    @Bean
    @ConditionalOnProperty(prefix = "venus.oss.minio", name = "enabled")
    public MinioOssService minioOssService(MinioProperties minioProperties) throws Exception {
        return new MinioOssService(minioProperties);
    }
}
