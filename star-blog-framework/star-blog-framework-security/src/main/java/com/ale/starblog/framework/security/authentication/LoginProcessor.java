package com.ale.starblog.framework.security.authentication;

import com.ale.starblog.framework.common.security.AuthenticatedUser;
import com.ale.starblog.framework.common.support.Supportable;
import com.ale.starblog.framework.security.enums.LoginType;

import java.util.Map;

/**
 * 登录处理器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/26
 **/
public interface LoginProcessor extends Supportable<LoginType> {

    /**
     * 登录
     *
     * @param parameters 登录参数
     * @return 认证用户
     */
    AuthenticatedUser login(Map<String, Object> parameters);
}
