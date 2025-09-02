package com.ale.starblog.framework.common.transaction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 事务支持初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/25 9:08
 */
public class TransactionSupportInitializer implements BeanPostProcessor {

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof PlatformTransactionManager) {
            try {
                MethodHandles.privateLookupIn(TransactionSupport.class, MethodHandles.lookup())
                    .findStatic(TransactionSupport.class, "setTransactionManager", MethodType.methodType(void.class, PlatformTransactionManager.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化TransactionSupport失败!");
            }
        }
        return bean;
    }
}
