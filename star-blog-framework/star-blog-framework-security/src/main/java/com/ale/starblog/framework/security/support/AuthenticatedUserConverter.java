package com.ale.starblog.framework.security.support;

import com.ale.starblog.framework.common.security.AuthenticatedUser;

import java.util.Map;

/**
 * 认证用户转换器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/25
 **/
public interface AuthenticatedUserConverter {

    /**
     * 转换认证用户
     *
     * @param userInfo 用户信息Map
     * @return 认证用户对象
     */
    AuthenticatedUser convertToUser(Map<String, Object> userInfo);

    /**
     * 转换用户信息Map
     *
     * @param authenticatedUser 认证用户
     * @return 用户信息Map
     */
    Map<String, Object> convertToMap(AuthenticatedUser authenticatedUser);

}
