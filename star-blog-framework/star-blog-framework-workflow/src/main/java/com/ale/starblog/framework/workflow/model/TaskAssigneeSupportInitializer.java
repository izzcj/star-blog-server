package com.ale.starblog.framework.workflow.model;

import com.ale.starblog.framework.workflow.parser.TaskAssigneeParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 任务受理人支持初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/6/30 14:26
 */
public class TaskAssigneeSupportInitializer implements BeanPostProcessor {

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof TaskAssigneeParser) {
            try {
                MethodHandles.privateLookupIn(TaskAssigneeSupport.class, MethodHandles.lookup())
                    .findStatic(TaskAssigneeSupport.class, "setAssigneeParser", MethodType.methodType(void.class, TaskAssigneeParser.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new RuntimeException("初始化任务受理人支持失败！");
            }
        }
        return bean;
    }
}
