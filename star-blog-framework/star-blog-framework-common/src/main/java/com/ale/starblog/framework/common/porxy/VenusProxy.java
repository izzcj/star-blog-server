package com.ale.starblog.framework.common.porxy;

import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;

/**
 * Venus代理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/29
 */
public interface VenusProxy {

    /**
     * 创建代理
     *
     * @param originalObject     原始对象
     * @param proxyMethodInvoker 代理方法回调器
     * @return 代理对象
     */
    Object createProxy(Object originalObject, ProxyMethodInvoker proxyMethodInvoker);

}
