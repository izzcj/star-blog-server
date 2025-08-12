package com.ale.starblog.framework.security.config;

import com.ale.starblog.framework.security.support.RequestUriDescriptor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * Venus安全配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Data
@ConfigurationProperties(prefix = "venus.security")
public class VenusSecurityProperties {

    /**
     * Token类型
     */
    public enum TokenType {
        /**
         * Jwt Token
         */
        JWT,
        /**
         * Redis Token
         */
        REDIS
    }

    /**
     * Token类型
     */
    private TokenType tokenType;

    /**
     * Token 过期时长
     */
    private Duration tokenExpiration;

    /**
     * 每次请求是否刷新Token过期时长
     */
    private boolean refreshTokenExpirationPerRequest;

    /**
     * 无需认证的URI
     */
    private List<RequestUriDescriptor> permittedUris;

    /**
     * 仅需要认证的URI
     */
    private List<RequestUriDescriptor> authenticatedUris;

}
