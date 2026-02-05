package com.ale.starblog.framework.core.oss;

import com.ale.starblog.framework.common.enumeration.BaseEnum;

/**
 * Oss服务实现提供器枚举
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:17
 */
public enum OssServiceProvider implements BaseEnum<String> {

    /**
     * minio
     */
    MINIO,

    /**
     * 阿里云OSS
     */
    ALIYUN;

    OssServiceProvider() {
        init();
    }
}
