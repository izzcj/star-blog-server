package com.ale.starblog.admin.common.utils;

import com.ale.starblog.admin.common.constants.SessionContextConstants;
import com.ale.starblog.admin.common.login.AuthUser;
import com.ale.starblog.framework.common.exception.AuthenticationNotExistException;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.security.context.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 认证工具类
 *
 * @author wanx
 * @version 1.0.0
 * @since 2023/1/6 星期五 19:38
 */
@Slf4j
@Component
public final class AuthenticationUtils {

    /**
     * 会话上下文
     */
    private static SessionContext sessionContext;

    AuthenticationUtils(SessionContext sessionContext) {
        AuthenticationUtils.sessionContext = sessionContext;
    }

    /**
     * 获取当前认证用户信息
     *
     * @return 认证用户对象
     */
    public static AuthUser getAuthenticatedUser() {
        AuthUser authenticatedUserImpl = (AuthUser) SecurityUtils.getLoginUser();
        if (authenticatedUserImpl == null) {
            throw new AuthenticationNotExistException("无法获取当前登录用户的信息，请排查线程执行上下文是否正确！");
        }

        return authenticatedUserImpl;
    }

    /**
     * 获取用户数据权限
     *
     * @return 数据权限
     */
    public static Map<String, Set<String>> getUserDataPermissions() {
        Map<String, Set<String>> dataPermissions = sessionContext.get(SessionContextConstants.USER_DATA_PERMISSIONS_KEY);

        return Objects.requireNonNullElse(dataPermissions, Collections.emptyMap());
    }

    /**
     * 获取用户功能权限
     *
     * @return 功能权限集合
     */
    public static Set<String> getUserFunctionPermissions() {
        Set<String> functionPermissions = sessionContext.get(SessionContextConstants.USER_FUNCTION_PERMISSIONS_KEY);

        return Objects.requireNonNullElse(functionPermissions, Collections.emptySet());
    }

    /**
     * 获取登录用户ID
     *
     * @return 用户编号
     */
    public static Long getLoginUserId() {
        return getAuthenticatedUser().getId();
    }

    /**
     * 获取登录用户名称
     *
     * @return 用户名称
     */
    public static String getLoginUserName() {
        return getAuthenticatedUser().getName();
    }

    /**
     * 获取登录用户角色ID集合
     *
     * @return 角色编号集合
     */
    public static Set<Long> getLoginUserRoleIds() {
        return getAuthenticatedUser().getRoleIds();
    }
}
