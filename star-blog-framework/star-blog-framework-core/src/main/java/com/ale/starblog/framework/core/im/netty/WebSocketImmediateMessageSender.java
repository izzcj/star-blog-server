package com.ale.starblog.framework.core.im.netty;

import com.ale.starblog.framework.core.im.ImmediateMessage;
import com.ale.starblog.framework.core.im.ImmediateMessageSender;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 基于WebSocket的即时消息发送器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 15:26
 */
public class WebSocketImmediateMessageSender implements ImmediateMessageSender {
    /**
     * 通道管理器
     */
    private final ChannelManager channelManager;

    public WebSocketImmediateMessageSender(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    @Override
    public void send(ImmediateMessage msg) {
        Channel channel = this.channelManager.get(msg.getTo());
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
        }
    }
}
