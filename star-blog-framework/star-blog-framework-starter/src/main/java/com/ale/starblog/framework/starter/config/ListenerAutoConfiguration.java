package com.ale.starblog.framework.starter.config;

import com.ale.starblog.framework.starter.listener.VenusFrameworkBannerPrintlnListener;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 监听器自动配置类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/5/15
 **/
@AutoConfiguration
public class ListenerAutoConfiguration {

    /**
     * VenusBannerPrintlnListener
     *
     * @return {@link VenusFrameworkBannerPrintlnListener}
     */
    @Bean
    public VenusFrameworkBannerPrintlnListener venusFrameworkBannerPrintlnListener() {
        return new VenusFrameworkBannerPrintlnListener();
    }

}
