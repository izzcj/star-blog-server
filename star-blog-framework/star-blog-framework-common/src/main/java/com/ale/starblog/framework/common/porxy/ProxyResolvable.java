package com.ale.starblog.framework.common.porxy;

import com.ale.starblog.framework.common.utils.CastUtils;
import org.springframework.aop.framework.AopContext;

/**
 * 代理实例解析能力
 *
 * @param <T> 代理实例类型
 * @author Ale
 * @version 1.0.0 2025/5/30 17:20
 */
public interface ProxyResolvable<T> {

    /**
     * 解析代理对象
     *
     * @return 代理对象
     */
    default T resolveProxy() {
        return CastUtils.cast(AopContext.currentProxy());
    }

}
