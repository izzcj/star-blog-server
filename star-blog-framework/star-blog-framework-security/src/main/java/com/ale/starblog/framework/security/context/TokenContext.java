package com.ale.starblog.framework.security.context;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * token上下文
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/25
 **/
@Data
@AllArgsConstructor
public class TokenContext {

    /**
     * 访问Token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
}
