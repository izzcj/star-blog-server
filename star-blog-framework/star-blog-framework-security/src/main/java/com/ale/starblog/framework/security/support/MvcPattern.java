package com.ale.starblog.framework.security.support;

import com.ale.starblog.framework.common.enumeration.RequestMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Mvc路径模式
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Data
@AllArgsConstructor
public class MvcPattern {

    /**
     * 请求方法
     */
    private RequestMethod method;

    /**
     * 请求路径
     */
    private String pattern;
}
