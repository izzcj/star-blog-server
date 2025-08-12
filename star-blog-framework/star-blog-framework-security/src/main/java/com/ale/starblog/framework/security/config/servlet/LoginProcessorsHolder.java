package com.ale.starblog.framework.security.config.servlet;

import com.ale.starblog.framework.security.authentication.LoginProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 登录处理器持有器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Getter
@Component
@RequiredArgsConstructor
public class LoginProcessorsHolder {

    /**
     * 登录处理器
     */
    private final ObjectProvider<LoginProcessor> loginProcessors;

}
