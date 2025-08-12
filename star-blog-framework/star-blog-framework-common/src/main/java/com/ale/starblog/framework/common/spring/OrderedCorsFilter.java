package com.ale.starblog.framework.common.spring;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 带顺序的CORS过滤器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/5
 **/
public class OrderedCorsFilter extends CorsFilter implements OrderedFilter {

    /**
     * 默认排序
     */
    public static final int DEFAULT_ORDER = OrderedFormContentFilter.DEFAULT_ORDER - 9900;

    /**
     * Constructor accepting a {@link CorsConfigurationSource} used by the filter
     * to find the {@link CorsConfiguration} to use for each incoming request.
     *
     * @param configSource the configSource
     * @see UrlBasedCorsConfigurationSource
     */
    public OrderedCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
