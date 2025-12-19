package com.ale.starblog.framework.core.im;
import com.ale.starblog.framework.common.security.TokenManager;
import com.ale.starblog.framework.core.im.mq.MessageConsumer;
import com.ale.starblog.framework.core.im.mq.MessageProducer;
import com.ale.starblog.framework.core.im.netty.*;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 实时消息自动配置
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:25
 */
@AutoConfiguration
@EnableConfigurationProperties(ImmediateMessageProperties.class)
public class ImmediateMessageAutoConfig {

    /**
     * 通道管理器
     *
     * @return {@link ChannelManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public ChannelManager channelManager() {
        return new SimpleChannelManager();
    }

    /**
     * 群组管理器
     *
     * @return {@link GroupManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public GroupManager groupManager() {
        return new SimpleGroupManager();
    }

    /**
     * 鉴权处理器
     *
     * @param tokenManager token管理器
     * @return {@link AuthHandler}
     */
    @Bean
    public AuthHandler authHandler(TokenManager tokenManager) {
        return new AuthHandler(tokenManager);
    }

    /**
     * 实时消息发送器
     *
     * @param channelManager 通道管理器
     * @return {@link ImmediateMessageSender}
     */
    @Bean
    @ConditionalOnMissingBean
    public ImmediateMessageSender immediateMessageSender(ChannelManager channelManager) {
        return new WebSocketImmediateMessageSender(channelManager);
    }

    /**
     * WebSocket处理器
     *
     * @param channelManager         通道管理器
     * @param groupManager           群组管理器
     * @param immediateMessageSender 即时消息发送器
     * @return {@link WebSocketHandler}
     */
    @Bean
    public WebSocketHandler webSocketHandler(ChannelManager channelManager, GroupManager groupManager, ImmediateMessageSender immediateMessageSender) {
        return new WebSocketHandler(channelManager, groupManager, immediateMessageSender);
    }

    /**
     * WebSocket初始化器
     *
     * @param authHandler      鉴权处理器
     * @param webSocketHandler WebSocket处理器
     * @return {@link WebSocketInitializer}
     */
    @Bean
    @ConditionalOnBean(WebSocketHandler.class)
    public WebSocketInitializer webSocketInitializer(AuthHandler authHandler, WebSocketHandler webSocketHandler) {
        return new WebSocketInitializer(authHandler, webSocketHandler);
    }

    /**
     * Netty服务端
     *
     * @param webSocketInitializer WebSocket初始化器
     * @return {@link NettyServer}
     */
    @Bean
    @ConditionalOnBean(WebSocketInitializer.class)
    public NettyServer nettyServer(WebSocketInitializer webSocketInitializer) {
        return new NettyServer(webSocketInitializer);
    }

    /**
     * RabbitMq即时消息自动配置
     */
    @RequiredArgsConstructor
    @ConditionalOnClass({ RabbitTemplate.class, Channel.class })
    public static class RabbitMqImmediateMessageConfiguration {

        /**
         * 交换机
         */
        public static final String EXCHANGE = "im.exchange";

        /**
         * 队列
         */
        public static final String QUEUE = "im.queue";

        /**
         * 创建交换机
         *
         * @return {@link FanoutExchange}
         */
        @Bean
        public FanoutExchange exchange() {
            return new FanoutExchange(EXCHANGE);
        }

        /**
         * 创建队列
         *
         * @return {@link Queue}
         */
        @Bean
        public Queue queue() {
            return new Queue(QUEUE);
        }

        /**
         * 绑定队列和交换机
         *
         * @return {@link Binding}
         */
        @Bean
        public Binding binding() {
            return BindingBuilder.bind(queue()).to(exchange());
        }

        /**
         * 消息转换器
         *
         * @return {@link MessageConverter}
         */
        @Bean
        public MessageConverter jackson2JsonMessageConverter() {
            return new Jackson2JsonMessageConverter();
        }

        /**
         * 即时消息发送器
         *
         * @param rabbitTemplate RabbitMq模板
         * @return {@link ImmediateMessageSender}
         */
        @Bean
        public ImmediateMessageSender immediateMessageSender(RabbitTemplate rabbitTemplate) {
            return new MessageProducer(rabbitTemplate);
        }

        /**
         * 消息消费者
         *
         * @param channelManager 通道管理器
         * @param groupManager   群组管理器
         * @return {@link MessageConsumer}
         */
        @Bean
        public MessageConsumer messageConsumer(ChannelManager channelManager, GroupManager groupManager) {
            return new MessageConsumer(channelManager, groupManager);
        }
    }

}
