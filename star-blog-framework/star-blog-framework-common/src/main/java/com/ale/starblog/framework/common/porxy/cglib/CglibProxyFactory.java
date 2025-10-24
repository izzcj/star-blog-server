package com.ale.starblog.framework.common.porxy.cglib;

import com.ale.starblog.framework.common.porxy.ProxyFactory;
import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;

/**
 * Cglib代理工厂
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29 星期二 11:41
 */
public class CglibProxyFactory implements ProxyFactory {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Object createProxy(Object originalObject, ProxyMethodInvoker proxyMethodInvoker) {
        return new CglibProxy().createProxy(originalObject, proxyMethodInvoker);
    }
}
