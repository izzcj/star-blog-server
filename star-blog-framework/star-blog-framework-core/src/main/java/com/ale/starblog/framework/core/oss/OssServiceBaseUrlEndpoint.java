package com.ale.starblog.framework.core.oss;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.support.facade.RequestMethodMatcher;
import com.ale.starblog.framework.common.support.facade.RequestUriMatcher;
import com.ale.starblog.framework.core.endpoint.Endpoint;
import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * OSS对象存储服务访问的基础URL端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:10
 */
@RequiredArgsConstructor
public class OssServiceBaseUrlEndpoint implements Endpoint {

    /**
     * 获取OSS对象服务实现的访问基础URL路径
     */
    private static final String OSS_BASE_URL_PATH = "/oss/base-urls";
    /**
     * OSS对象存储服务实现
     */
    private final ObjectProvider<OssService> ossServices;

    @Override
    public RequestUriMatcher getRequestUriMatcher() {
        return uri -> StrUtil.equals(OSS_BASE_URL_PATH, uri);
    }

    @Override
    public RequestMethodMatcher getRequestMethodMatcher() {
        return method -> HttpMethod.GET == method;
    }

    @Override
    public Result<?> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OssServiceProvider[] providers = OssServiceProvider.values();
        Map<String, String> baseUrlMap = Maps.newHashMapWithExpectedSize(providers.length);
        for (OssServiceProvider provider : providers) {
            for (OssService ossService : this.ossServices) {
                if (ossService.supports(provider)) {
                    baseUrlMap.put(
                        NamingCase.toKebabCase(
                            provider.name().toLowerCase()
                        ),
                        ossService.getBaseUrl()
                    );
                }
            }
        }

        return JsonResult.success(baseUrlMap);
    }
}
