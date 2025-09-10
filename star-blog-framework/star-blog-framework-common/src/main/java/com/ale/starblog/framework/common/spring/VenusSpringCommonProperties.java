package com.ale.starblog.framework.common.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Venus WebMvc配置
 *
 * @author Ale
 * @version 1.0.0 2025/9/10 14:05
 */
@Data
@ConfigurationProperties(prefix = "venus.common")
public class VenusSpringCommonProperties {

    /**
     * 是否启用跨域
     */
    private boolean enableCors;

}
