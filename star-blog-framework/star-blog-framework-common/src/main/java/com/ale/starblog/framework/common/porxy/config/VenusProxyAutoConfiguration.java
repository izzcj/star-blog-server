package com.ale.starblog.framework.common.porxy.config;

import cn.hutool.core.util.BooleanUtil;
import com.ale.starblog.framework.common.porxy.ProxyFactory;
import com.ale.starblog.framework.common.porxy.ComponentScanMark;
import com.ale.starblog.framework.common.porxy.cglib.CglibProxyFactory;
import com.ale.starblog.framework.common.porxy.jdk.JDKProxyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Venus代理自动配置
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 9:14
 */
@AutoConfiguration
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = ComponentScanMark.class)
@EnableConfigurationProperties(VenusProxyProperties.class)
public class VenusProxyAutoConfiguration {

    /**
     * 代理工厂
     *
     * @param context Spring上下文
     * @return Venus代理Bean
     */
    @Bean
    @ConditionalOnProperty(prefix = "venus.proxy", name = "enabled", havingValue = "true")
    public ProxyFactory proxyFactory(ConfigurableApplicationContext context) {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        BeanDefinition definition = registry.getBeanDefinition(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME);
        MutablePropertyValues propertyValues = definition.getPropertyValues();
        boolean proxyTargetClass = false;
        for (PropertyValue propertyValue : propertyValues) {
            if ("proxyTargetClass".equals(propertyValue.getName())) {
                proxyTargetClass = (Boolean) propertyValue.getValue();
                break;
            }
        }
        if (BooleanUtil.isTrue(proxyTargetClass)) {
            return new CglibProxyFactory();
        } else {
            return new JDKProxyFactory();
        }
    }
}
