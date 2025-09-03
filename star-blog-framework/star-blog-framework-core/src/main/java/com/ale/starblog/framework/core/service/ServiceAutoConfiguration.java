package com.ale.starblog.framework.core.service;

import com.ale.starblog.framework.common.porxy.ProxyFactory;
import com.ale.starblog.framework.common.porxy.config.VenusProxyAutoConfiguration;
import com.ale.starblog.framework.core.service.proxy.ServiceProxyInitializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Service自动配置类
 *
 * @author Ale
 * @version 1.0.0 2025/5/28 15:22
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = ComponentScanMark.class)
@AutoConfigureAfter(VenusProxyAutoConfiguration.class)
public class ServiceAutoConfiguration {

    /**
     * Service代理初始化器bean
     *
     * @param proxyFactoryObjectProvider 代理工厂提供器
     * @return Service代理初始化器
     */
    @Bean
    @ConditionalOnBean(ProxyFactory.class)
    public static ServiceProxyInitializer serviceProxyInitializer(ObjectProvider<ProxyFactory> proxyFactoryObjectProvider) {
        return new ServiceProxyInitializer(proxyFactoryObjectProvider);
    }
}
