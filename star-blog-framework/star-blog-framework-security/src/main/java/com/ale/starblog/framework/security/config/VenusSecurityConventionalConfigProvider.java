package com.ale.starblog.framework.security.config;

import com.ale.starblog.framework.common.config.ConventionalConfigProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * Venus安全框架配置提供器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
public class VenusSecurityConventionalConfigProvider implements ConventionalConfigProvider {

    @Override
    public Map<String, Object> provide(ConfigurableEnvironment environment, SpringApplication application) {
        return new HashMap<>();
    }
}
