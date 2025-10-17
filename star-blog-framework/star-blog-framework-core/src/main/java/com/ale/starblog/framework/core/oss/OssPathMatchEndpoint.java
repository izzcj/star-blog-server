package com.ale.starblog.framework.core.oss;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.support.RequestUriMatcher;
import com.ale.starblog.framework.core.endpoint.Endpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Map;

/**
 * OSS路径匹配端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:22
 */
@RequiredArgsConstructor
public abstract class OssPathMatchEndpoint implements Endpoint {

    /**
     * 路径匹配器
     */
    protected final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * OSS对象存储服务实现
     */
    private final ObjectProvider<OssService> ossServices;

    @Override
    public RequestUriMatcher getRequestUriMatcher() {
        return uri -> this.pathMatcher.match(this.providePathPattern(), uri);
    }

    @Override
    public Result<?> handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> variables = this.pathMatcher.extractUriTemplateVariables(
            this.providePathPattern(),
            request.getRequestURI()
        );
        String ossProvider = variables.get("ossProvider");
        if (StrUtil.isBlank(ossProvider)) {
            return JsonResult.fail("OSS实现类型不能为空");
        }
        OssServiceProvider serviceProvider;
        try {
            serviceProvider = OssServiceProvider.valueOf(ossProvider.toUpperCase());
        } catch (IllegalArgumentException e) {
            return JsonResult.fail("不支持的OSS实现类型：{}", ossProvider);
        }
        for (OssService ossService : this.ossServices) {
            if (ossService.supports(serviceProvider)) {
                return this.handle(request, response, ossService, variables);
            }
        }

        return JsonResult.fail("暂未实现或未启用OSS类型：{}", ossProvider);
    }

    /**
     * 提供路径模式
     *
     * @return 路径模式
     */
    protected abstract String providePathPattern();

    /**
     * 处理请求
     *
     * @param request    请求对象
     * @param response   响应对象
     * @param ossService OSS服务实现
     * @param variables  路径变量
     * @return 结果对象
     */
    protected abstract Result<?> handle(HttpServletRequest request, HttpServletResponse response, OssService ossService, Map<String, String> variables) throws Exception;
}
