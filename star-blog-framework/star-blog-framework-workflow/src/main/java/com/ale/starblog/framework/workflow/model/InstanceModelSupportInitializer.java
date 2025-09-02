package com.ale.starblog.framework.workflow.model;

import com.ale.starblog.framework.workflow.cache.FlowEngineCache;
import com.ale.starblog.framework.workflow.parser.InstanceModelParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 流程实例模型支持类初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/12 9:12
 */
public class InstanceModelSupportInitializer implements BeanPostProcessor {

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof InstanceModelParser) {
            try {
                MethodHandles.privateLookupIn(InstanceModelSupport.class, MethodHandles.lookup())
                    .findStatic(InstanceModelSupport.class, "setInstanceModelParser", MethodType.methodType(void.class, InstanceModelParser.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化流程实例模型支持类失败！");
            }
        }
        if (bean instanceof FlowEngineCache) {
            try {
                MethodHandles.privateLookupIn(InstanceModelSupport.class, MethodHandles.lookup())
                    .findStatic(InstanceModelSupport.class, "setFlowEngineCache", MethodType.methodType(void.class, FlowEngineCache.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化流程引擎缓存失败", e);
            }
        }
        return bean;
    }
}
