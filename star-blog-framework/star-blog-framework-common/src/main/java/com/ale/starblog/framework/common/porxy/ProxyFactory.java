package com.ale.starblog.framework.common.porxy;

import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;

/**
 * 代理工厂
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 11:35
 */
public interface ProxyFactory {

    /**
     * 创建代理对象
     *
     * @param originalObject     原始对象
     * @param proxyMethodInvoker 代理方法调用器
     * @return 代理工厂实例
     */
    Object createProxy(Object originalObject, ProxyMethodInvoker proxyMethodInvoker);
}
