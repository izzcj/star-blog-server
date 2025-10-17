package com.ale.starblog.framework.common.porxy.jdk;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.porxy.AbstractProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于JDK的动态代理
 *
 * @param <T> 原始对象类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 9:31
 */
public class JDKProxy<T> extends AbstractProxy<T> implements InvocationHandler {

    @Override
    protected Object create(T originalObject) {
        if (AopUtils.isCglibProxy(originalObject)) {
            throw new UnsupportedOperationException(StrUtil.format("被代理类[{}]为Cglib代理类，无法使用Jdk代理", originalObject.getClass()));
        }
        Class<?> originalObjectClass = this.originalObject.getClass();
        return Proxy.newProxyInstance(
            originalObjectClass.getClassLoader(),
            originalObjectClass.getInterfaces(),
            this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.methodInvoke(method, args);
    }
}
