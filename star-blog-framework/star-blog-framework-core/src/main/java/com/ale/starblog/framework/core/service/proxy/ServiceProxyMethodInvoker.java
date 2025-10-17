package com.ale.starblog.framework.core.service.proxy;

import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;
import com.ale.starblog.framework.core.service.ICrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

/**
 * Service代理方法回调器
 * 预留用，目前没有用到
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/28 星期一 17:48
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes"})
public class ServiceProxyMethodInvoker implements ProxyMethodInvoker<ICrudService> {

    @Override
    public boolean before(Object proxy, ICrudService target, Method method, Object[] args) {
        return true;
    }

    @Override
    public boolean after(Object proxy, ICrudService target, Method method, Object[] args, Object returnValue) {
        return true;
    }

    @Override
    public boolean afterException(Object proxy, ICrudService target, Method method, Object[] args, Throwable throwable) {
        return true;
    }
}
