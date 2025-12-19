package com.ale.starblog.framework.core.im.mq;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.core.im.ImmediateMessage;
import com.ale.starblog.framework.core.im.ImmediateMessageAutoConfig;
import com.ale.starblog.framework.core.im.MessageType;
import com.ale.starblog.framework.core.im.netty.ChannelManager;
import com.ale.starblog.framework.core.im.GroupManager;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Collection;

/**
 * 消息消费者
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:29
 */
@Slf4j
public class MessageConsumer {

    /**
     * 通道管理器
     */
    private final ChannelManager channelManager;

    /**
     * 群组管理器
     */
    private final GroupManager groupManager;

    public MessageConsumer(ChannelManager channelManager, GroupManager groupManager) {
        this.channelManager = channelManager;
        this.groupManager = groupManager;
    }

    /**
     * 接收消息
     *
     * @param msg 消息
     */
    @RabbitListener(queues = ImmediateMessageAutoConfig.RabbitMqImmediateMessageConfiguration.QUEUE)
    public void receive(ImmediateMessage msg) {
        MessageType messageType = msg.getMessage().getType();
        String messageJson = JsonUtils.toJson(msg);
        if (MessageType.GROUP_CHAT.match(messageType)) {
            Collection<String> groupMembers = this.groupManager.getGroupMembers(msg.getTo());
            if (CollectionUtil.isEmpty(groupMembers)) {
                return;
            }
            for (String groupMember : groupMembers) {
                this.sendMessage(groupMember, messageJson);
            }
            return;
        }
        this.sendMessage(msg.getTo(), messageJson);
    }

    /**
     * 发送消息
     *
     * @param userId  用户ID
     * @param message 消息
     */
    private void sendMessage(String userId, String message) {
        Channel channel = this.channelManager.get(userId);
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }

}
