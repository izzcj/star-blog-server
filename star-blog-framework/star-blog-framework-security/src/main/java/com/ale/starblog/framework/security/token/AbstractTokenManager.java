package com.ale.starblog.framework.security.token;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.security.TokenManager;
import com.ale.starblog.framework.security.contanst.SecurityConstants;

/**
 * 抽象token管理器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/25
 **/
public abstract class AbstractTokenManager implements TokenManager {

    @Override
    public String extractAccessToken(String authorization) {
        if (StrUtil.isBlank(authorization)) {
            return null;
        }
        if (authorization.startsWith(SecurityConstants.BEARER_PREFIX)) {
            authorization = StrUtil.subAfter(authorization, SecurityConstants.BEARER_PREFIX, false);
        }
        return authorization;
    }

}
