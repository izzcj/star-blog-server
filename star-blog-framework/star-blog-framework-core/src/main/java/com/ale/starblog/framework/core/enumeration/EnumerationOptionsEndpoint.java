package com.ale.starblog.framework.core.enumeration;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.enumeration.EnumHolder;
import com.ale.starblog.framework.common.support.RequestMethodMatcher;
import com.ale.starblog.framework.common.support.RequestUriMatcher;
import com.ale.starblog.framework.core.endpoint.Endpoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 获取枚举选项端点
 *
 * @author Ale
 * @version 1.0.0 2025/10/29 14:53
 */
public class EnumerationOptionsEndpoint implements Endpoint {

    /**
     * 获取枚举选项URL路径
     */
    private static final String ENUMERATION_OPTIONS_URL_PATH = "/enumeration/options";

    @Override
    public RequestUriMatcher getRequestUriMatcher() {
        return url -> StrUtil.equals(ENUMERATION_OPTIONS_URL_PATH, url);
    }

    @Override
    public RequestMethodMatcher getRequestMethodMatcher() {
        return method -> HttpMethod.GET == method;
    }

    @Override
    public Result<?> handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String className = request.getParameter("class");
            if (StrUtil.isBlank(className)) {
                var classes = request.getParameter("classes");
                if (StrUtil.isBlank(classes)) {
                    return JsonResult.fail("获取枚举选项失败：查询参数[class/classes]不能为空");
                }

                String[] classNames = StringUtils.split(classes, StringConstants.COMMA);
                return JsonResult.success(
                    Arrays.stream(classNames)
                        .collect(Collectors.toMap(Function.identity(), EnumHolder::getEnumOptions))
                );
            } else {
                return JsonResult.success(EnumHolder.getEnumOptions(className));
            }
        } catch (Exception e) {
            return JsonResult.fail("获取枚举选项失败：{}", e.getMessage());
        }
    }
}
