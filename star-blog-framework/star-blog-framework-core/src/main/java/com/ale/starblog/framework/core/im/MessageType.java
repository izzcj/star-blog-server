package com.ale.starblog.framework.core.im;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 消息内容
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:35
 */
@Getter
public enum MessageType implements BaseEnum<String> {

    /**
     * 心跳PING
     */
    PING,

    /**
     * 心跳PONG
     */
    PONG,

    /**
     * 加入群组
     */
    JOIN_GROUP,

    /**
     * 单聊
     */
    SINGLE_CHAT,

    /**
     * 群聊
     */
    GROUP_CHAT;

    MessageType() {
        this.init();
    }
}
