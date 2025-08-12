package com.ale.starblog.framework.common.porxy.cglib;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.framework.common.porxy.AbstractProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 基于Cglib的代理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/28 星期一 17:45
 */
public class CglibProxy extends AbstractProxy implements MethodInterceptor {

    /**
     * Enhancer
     */
    private final Enhancer enhancer = new Enhancer();

    @Override
    protected Object create(Object originalObject) {
        if (AopUtils.isJdkDynamicProxy(originalObject)) {
            throw new UnsupportedOperationException(StrUtil.format("被代理类[{}]为Jdk代理类，无法使用Cglib代理", originalObject.getClass()));
        }
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Class<?> objectClass = originalObject.getClass();
        enhancer.setSuperclass(objectClass.getSuperclass());
        enhancer.setCallback(this);
        Constructor<?>[] constructors = objectClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return enhancer.create();
            }
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] constructorArgs = Arrays.stream(parameterTypes)
                .map(applicationContext::getBean)
                .toArray();
            if (constructorArgs.length == parameterTypes.length) {
                return enhancer.create(parameterTypes, constructorArgs);
            }
        }
        throw new RuntimeException(StrUtil.format("无法代理类：{}", objectClass.getName()));
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return this.methodInvoke(method, args);
    }
}
