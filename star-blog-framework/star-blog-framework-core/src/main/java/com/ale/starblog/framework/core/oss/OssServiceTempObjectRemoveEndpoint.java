package com.ale.starblog.framework.core.oss;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.support.RequestMethodMatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * OSS服务临时对象移除处理端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:12
 */
public class OssServiceTempObjectRemoveEndpoint extends OssPathMatchEndpoint {

    /**
     * 获取OSS对象服务实现的移除临时上传对象的路径模式
     */
    private static final String OSS_TEMP_OBJECT_REMOVAL_PATH_PATTERN = "/oss/temp/{ossProvider}";

    public OssServiceTempObjectRemoveEndpoint(ObjectProvider<OssService> ossServices) {
        super(ossServices);
    }

    @Override
    public RequestMethodMatcher getRequestMethodMatcher() {
        return method -> HttpMethod.DELETE == method;
    }

    @Override
    protected String providePathPattern() {
        return OSS_TEMP_OBJECT_REMOVAL_PATH_PATTERN;
    }

    @Override
    protected Result<?> handle(HttpServletRequest request, HttpServletResponse response, OssService ossService, Map<String, String> variables) {
        String objectKey = request.getParameter("objectKey");
        if (StrUtil.isBlank(objectKey)) {
            return JsonResult.fail("要移除临时对象文件的Key不能为空");
        }

        if (StrUtil.startWith(objectKey, OssService.TEMPORARY_OBJECT_KEY_PREFIX)) {
            ossService.remove(objectKey);
        }
        return JsonResult.success("临时上传文件移除成功");
    }
}
