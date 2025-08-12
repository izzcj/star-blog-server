package com.ale.starblog.framework.security.support;

import java.util.List;

/**
 * Mvc路径模式提供器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/7/9
 **/
public interface MvcPatternProvider {

    /**
     * 提供Mvc路径模式
     *
     * @return Mvc路径模式列表
     */
    List<MvcPattern> provide();
}
