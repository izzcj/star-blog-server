package com.ale.starblog.framework.security.authorization.servlet;

import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.exception.ExceptionCode;
import com.ale.starblog.framework.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 请求访问拒绝处理器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Slf4j
public class VenusAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("访问被拒：{}", accessDeniedException.getMessage());
        ServletUtils.responseJson(
            response,
            HttpStatus.FORBIDDEN,
            JsonResult.fail(ExceptionCode.UNAUTHORIZED)
        );
    }
}
