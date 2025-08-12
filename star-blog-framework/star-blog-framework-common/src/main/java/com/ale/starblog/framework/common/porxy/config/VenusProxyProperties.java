package com.ale.starblog.framework.common.porxy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Venus代理配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 9:43
 */
@Data
@ConfigurationProperties(prefix = "venus.proxy")
public class VenusProxyProperties {

    /**
     * 是否开启代理
     */
    private boolean enabled;

}
