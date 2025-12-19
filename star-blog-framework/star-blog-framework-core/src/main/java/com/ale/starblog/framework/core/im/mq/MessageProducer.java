package com.ale.starblog.framework.core.im.mq;

import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.core.im.ImmediateMessage;
import com.ale.starblog.framework.core.im.ImmediateMessageAutoConfig;
import com.ale.starblog.framework.core.im.ImmediateMessageSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息生产者
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:27
 */
public class MessageProducer implements ImmediateMessageSender {

    /**
     * RabbitMqTemplate
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 构造函数
     *
     * @param template 模板
     */
    public MessageProducer(RabbitTemplate template) {
        this.rabbitTemplate = template;
    }

    @Override
    public void send(ImmediateMessage msg) {
        this.rabbitTemplate.convertAndSend(ImmediateMessageAutoConfig.RabbitMqImmediateMessageConfiguration.EXCHANGE, StringConstants.EMPTY, msg);
    }

}
