package com.ale.starblog.framework.core.im;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * IM消息
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImmediateMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1213245L;

    /**
     * 消息来源
     */
    private String from;

    /**
     * 消息目标
     */
    private String to;

    /**
     * 消息
     */
    private Message message;

    /**
     * 消息内容
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message implements Serializable {

        @Serial
        private static final long serialVersionUID = 56465451212L;

        /**
         * 消息类型
         */
        private MessageType type;

        /**
         * 消息内容
         */
        private String content;
    }

}
