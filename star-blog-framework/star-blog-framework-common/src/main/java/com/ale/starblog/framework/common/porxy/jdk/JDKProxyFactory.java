package com.ale.starblog.framework.common.porxy.jdk;

import com.ale.starblog.framework.common.porxy.ProxyFactory;
import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;

/**
 * JDK代理工厂
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 11:45
 */
public class JDKProxyFactory implements ProxyFactory {

    @Override
    public Object createProxy(Object originalObject, ProxyMethodInvoker proxyMethodInvoker) {
        return new JDKProxy().createProxy(originalObject, proxyMethodInvoker);
    }
}
