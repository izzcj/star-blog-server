package com.ale.starblog.framework.common.porxy;

import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 抽象代理类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 9:17
 */
public abstract class AbstractProxy implements VenusProxy {

    /**
     * 原始对象
     */
    protected Object originalObject;

    /**
     * 代理方法回调器
     */
    protected ProxyMethodInvoker proxyMethodInvoker;

    @Override
    public Object createProxy(Object originalObject, ProxyMethodInvoker proxyMethodInvoker) {
        this.originalObject = originalObject;
        this.proxyMethodInvoker = proxyMethodInvoker;
        return this.create(originalObject);
    }

    /**
     * 创建代理
     *
     * @param originalObject 原始对象
     * @return 代理对象
     */
    protected abstract Object create(Object originalObject);

    /**
     * 代理方法回调
     *
     * @param method 方法
     * @param args   参数
     * @return 方法返回值
     * @throws Throwable 方法执行异常
     */
    protected Object methodInvoke(Method method, Object[] args) throws Throwable {
        if (this.proxyMethodInvoker != null) {
            Object result = null;
            if (this.proxyMethodInvoker.before(this.originalObject, this.originalObject, method, args)) {
                try {
                    result = method.invoke(this.originalObject, args);
                } catch (InvocationTargetException e) {
                    if (this.proxyMethodInvoker.afterException(this.originalObject, this.originalObject, method, args, e.getTargetException())) {
                        throw e.getTargetException();
                    }
                }
                if (this.proxyMethodInvoker.after(this.originalObject, this.originalObject, method, args, result)) {
                    return result;
                }
                return null;
            }
        }
        return method.invoke(this.originalObject, args);
    }
}
