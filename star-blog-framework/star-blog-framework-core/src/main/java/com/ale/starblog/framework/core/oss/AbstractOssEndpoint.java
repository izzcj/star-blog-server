package com.ale.starblog.framework.core.oss;

import com.ale.starblog.framework.core.endpoint.Endpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

/**
 * 抽象OSS端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:22
 */
@RequiredArgsConstructor
public abstract class AbstractOssEndpoint implements Endpoint {

    /**
     * OSS对象存储服务实现
     */
    private final ObjectProvider<OssService> ossServices;

    /**
     * OSS元信息服务
     */
    protected final OssMateService ossMateService;

    /**
     * 查找OSS服务实现
     *
     * @param provider OSS提供器
     * @return OSS服务实现
     */
    protected OssService findOssService(OssServiceProvider provider) {
        for (OssService ossService : this.ossServices) {
            if (ossService.supports(provider)) {
                return ossService;
            }
        }
        throw new IllegalArgumentException("暂未实现或未启用OSS类型：" + provider);
    }
}
