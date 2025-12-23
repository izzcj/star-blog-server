package com.ale.starblog.framework.core.im.mq;

import com.ale.starblog.framework.core.im.*;
import com.ale.starblog.framework.core.im.netty.ChannelManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 基于RabbitMq的消息消费者
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:29
 */
@Slf4j
public class RabbitMessageConsumer extends InstantMessageHandleSupport {

    public RabbitMessageConsumer(ChannelManager channelManager, GroupManager groupManager) {
        super(channelManager, groupManager);
    }

    /**
     * 接收消息
     *
     * @param message 消息
     */
    @RabbitListener(queues = InstantMessageAutoConfig.RabbitMqImmediateMessageConfiguration.QUEUE)
    public void receive(InstantMessage message) {
        super.handleMessage(message);
    }
}
