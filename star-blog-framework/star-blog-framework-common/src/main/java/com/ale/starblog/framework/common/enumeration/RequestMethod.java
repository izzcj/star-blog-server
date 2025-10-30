package com.ale.starblog.framework.common.enumeration;

/**
 * http请求方法枚举
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
public enum RequestMethod implements BaseEnum<String> {

    /**
     * 所有方法
     */
    ALL,
    /**
     * GET方法
     */
    GET,
    /**
     * POST方法
     */
    POST,
    /**
     * PUT方法
     */
    PUT,
    /**
     * DELETE方法
     */
    DELETE;

    RequestMethod() {
        init();
    }
}
