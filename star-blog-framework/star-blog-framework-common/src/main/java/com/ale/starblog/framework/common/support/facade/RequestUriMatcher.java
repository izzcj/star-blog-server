package com.ale.starblog.framework.common.support.facade;

/**
 * 请求URI匹配器
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:03
 */
public interface RequestUriMatcher {

    /**
     * 是否匹配请求URI
     *
     * @param uri 请求URI
     * @return bool
     */
    boolean matches(String uri);

}
