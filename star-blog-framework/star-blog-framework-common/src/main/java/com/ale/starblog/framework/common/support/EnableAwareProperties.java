package com.ale.starblog.framework.common.support;

import lombok.Data;

/**
 * 开关配置属性
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/12/5 星期一 18:46
 */
@Data
public abstract class EnableAwareProperties {

    /**
     * 是否开启
     */
    private boolean enabled;
}
