package com.ale.starblog.framework.common.exception.handler;

import com.ale.starblog.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常输出器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/5
 **/
@Slf4j
public class ExceptionLogger {

    /**
     * 输出异常日志
     *
     * @param throwable 异常
     */
    public void logException(Throwable throwable) {
        log.warn("{}异常：{}", throwable instanceof ServiceException ? "业务" : "Venus框架", throwable.getMessage(), throwable);
    }
}
