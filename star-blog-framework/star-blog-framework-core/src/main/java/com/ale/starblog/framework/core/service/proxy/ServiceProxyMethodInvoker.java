package com.ale.starblog.framework.core.service.proxy;

import com.ale.starblog.framework.common.porxy.invoker.ProxyMethodInvoker;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Service代理方法回调器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/28 星期一 17:48
 */
@Slf4j
public class ServiceProxyMethodInvoker implements ProxyMethodInvoker {


    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        return true;
    }

    @Override
    public boolean after(Object proxy, Object target, Method method, Object[] args, Object returnValue) {
        String methodName = method.getName();
        if (Objects.equals(methodName, "queryPage")) {
            log.info("总数量：{}", ((IPage<?>) returnValue).getTotal());
        }
        return true;
    }

    @Override
    public boolean afterException(Object proxy, Object target, Method method, Object[] args, Throwable throwable) {
        return true;
    }
}
