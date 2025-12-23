package com.ale.starblog.framework.core.im.netty;

import com.ale.starblog.framework.core.im.GroupManager;
import com.ale.starblog.framework.core.im.InstantMessage;
import com.ale.starblog.framework.core.im.InstantMessageHandleSupport;
import com.ale.starblog.framework.core.im.InstantMessageSender;

/**
 * 基于WebSocket的即时消息发送器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 15:26
 */
public class WebSocketInstantMessageSender extends InstantMessageHandleSupport implements InstantMessageSender {

    public WebSocketInstantMessageSender(ChannelManager channelManager, GroupManager groupManager) {
        super(channelManager, groupManager);
    }

    @Override
    public void send(InstantMessage message) {
        super.handleMessage(message);
    }
}
