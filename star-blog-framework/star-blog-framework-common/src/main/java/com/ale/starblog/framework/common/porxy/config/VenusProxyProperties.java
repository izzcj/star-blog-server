package com.ale.starblog.framework.common.porxy.config;

import com.ale.starblog.framework.common.support.EnableAwareProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Venus代理配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 9:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "venus.proxy")
public class VenusProxyProperties extends EnableAwareProperties {

}
