package com.ale.starblog.admin.common.filter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.listener.DailyStatInfoChangeEvent;
import com.ale.starblog.framework.common.utils.CacheUtils;
import com.ale.starblog.framework.common.utils.RedisUtils;
import com.ale.starblog.framework.core.constants.VenusFilterConstants;
import com.ale.starblog.framework.core.filter.VenusFilter;
import com.google.common.collect.Sets;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

/**
 * venus访问信息过滤器
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 15:26
 */
@Component
public class VenusAccessInfoFilter implements VenusFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String cacheKey = CacheUtils.buildCacheKeyWithPrefix(SystemConstants.ACCESS_IP_CACHE_KEY, LocalDate.now().format(DatePattern.NORM_DATE_FORMATTER));
        Set<String> accessedIps = RedisUtils.getIfAbsent(cacheKey, Sets::newHashSet);
        if (!accessedIps.contains(request.getRemoteAddr())) {
            accessedIps.add(request.getRemoteAddr());
            RedisUtils.set(cacheKey, accessedIps, Duration.ofDays(1));
            SpringUtil.publishEvent(new DailyStatInfoChangeEvent(this, StatInfoConstants.STAT_INFO_TYPE_VIEW_COUNT, 1));
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return VenusFilterConstants.VENUS_ENDPOINT_FILTER_ORDER + 1;
    }
}
