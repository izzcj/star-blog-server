package com.ale.starblog.framework.workflow.executor;

import com.ale.starblog.framework.workflow.parser.ConditionParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 条件执行器支持初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/27 9:06
 */
public class ConditionExecutorSupportInitializer implements BeanPostProcessor {

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof ConditionExecutor) {
            try {
                MethodHandles.privateLookupIn(ConditionExecutor.class, MethodHandles.lookup())
                    .findStatic(ConditionExecutorSupport.class, "setConditionExecutor", MethodType.methodType(void.class, ConditionExecutor.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化条件执行器支持类失败！");
            }
        }
        if (bean instanceof ConditionParser) {
            try {
                MethodHandles.privateLookupIn(ConditionExecutorSupport.class, MethodHandles.lookup())
                    .findStatic(ConditionExecutorSupport.class, "setConditionParser", MethodType.methodType(void.class, ConditionParser.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化条件执行器支持类失败！");
            }
        }
        return bean;
    }
}
