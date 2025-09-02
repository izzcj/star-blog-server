package com.ale.starblog.framework.core.service.proxy;

import com.ale.starblog.framework.common.porxy.ProxyFactory;
import com.ale.starblog.framework.core.service.IBaseService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

/**
 * Service代理初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/5/28 15:20
 */
public class ServiceProxyInitializer implements BeanPostProcessor {

    /**
     * 代理工厂
     */
    private final ProxyFactory proxyFactory;

    public ServiceProxyInitializer(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof IBaseService) {
            return this.proxyFactory.createProxy(bean, new ServiceProxyMethodInvoker());
        }
        return bean;
    }
}
