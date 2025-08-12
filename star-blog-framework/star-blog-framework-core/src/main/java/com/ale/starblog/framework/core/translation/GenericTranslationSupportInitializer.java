package com.ale.starblog.framework.core.translation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 通用翻译支持初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 15:37
 */
public class GenericTranslationSupportInitializer implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof GenericTranslator) {
            try {
                MethodHandles.privateLookupIn(GenericTranslationSupport.class, MethodHandles.lookup())
                    .findStatic(GenericTranslationSupport.class, "registerGenericTranslator", MethodType.methodType(void.class, GenericTranslator.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new BeanInitializationException("初始化通用翻译支持失败");
            }
        }
        return bean;
    }
}
