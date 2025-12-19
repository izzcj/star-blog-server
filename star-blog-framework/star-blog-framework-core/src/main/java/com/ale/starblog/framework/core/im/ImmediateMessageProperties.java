package com.ale.starblog.framework.core.im;

import com.ale.starblog.framework.common.support.EnableAwareProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 实时消息配置属性
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 11:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "venus.im")
public class ImmediateMessageProperties extends EnableAwareProperties {

    /**
     * 消息发送器类型
     */
    private String senderType = "rabbit";

}
