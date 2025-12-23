package com.ale.starblog.framework.core.im.mq;

import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.core.im.InstantMessage;
import com.ale.starblog.framework.core.im.InstantMessageAutoConfig;
import com.ale.starblog.framework.core.im.InstantMessageSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 基于RabbitMq的消息生产者
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:27
 */
public class RabbitMessageProducer implements InstantMessageSender {

    /**
     * RabbitMqTemplate
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 构造函数
     *
     * @param template 模板
     */
    public RabbitMessageProducer(RabbitTemplate template) {
        this.rabbitTemplate = template;
    }

    @Override
    public void send(InstantMessage message) {
        this.rabbitTemplate.convertAndSend(InstantMessageAutoConfig.RabbitMqImmediateMessageConfiguration.EXCHANGE, StringConstants.EMPTY, message);
    }

}
