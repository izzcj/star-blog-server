package com.ale.starblog.framework.common.utils.init;

import com.ale.starblog.framework.common.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * JsonUtils初始化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/8/12
 */
public class JsonUtilsInitializer implements BeanPostProcessor {

    @NonNull
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof ObjectMapper) {
            try {
                MethodHandles.privateLookupIn(JsonUtils.class, MethodHandles.lookup())
                    .findStatic(JsonUtils.class, "initialize", MethodType.methodType(void.class, ObjectMapper.class))
                    .invoke(bean);
            } catch (Throwable e) {
                throw new BeanInitializationException("初始化JsonUtils失败！");
            }
        }
        return bean;
    }
}
