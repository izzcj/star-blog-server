package com.ale.starblog.framework.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Venus过滤器链
 *
 * @author Ale
 * @version 1.0.0 2025/9/29 9:24
 */

@RequiredArgsConstructor
public class VenusFilterChainFilter extends OncePerRequestFilter {

    /**
     * Venus过滤器
     */
    private final List<VenusFilter> venusFilters;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        new VenusFilterChain(filterChain, venusFilters).doFilter(request, response);
    }
}
