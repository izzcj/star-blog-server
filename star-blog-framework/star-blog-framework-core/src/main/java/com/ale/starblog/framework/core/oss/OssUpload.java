package com.ale.starblog.framework.core.oss;

import java.lang.annotation.*;

/**
 * OSS文件上传
 *
 * @author Ale
 * @version 1.0.0 2025/10/16 17:01
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OssUpload {

    /**
     * OSS服务提供器
     *
     * @return 服务提供器
     */
    OssServiceProvider provider() default OssServiceProvider.MINIO;

}
