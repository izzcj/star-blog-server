package com.ale.starblog.framework.core.im.netty;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.core.im.GroupManager;
import com.ale.starblog.framework.core.im.ImmediateMessage;
import com.ale.starblog.framework.core.im.ImmediateMessageSender;
import com.ale.starblog.framework.core.im.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket处理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:20
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 通道管理器
     */
    private final ChannelManager channelManager;

    /**
     * 群组管理器
     */
    private final GroupManager groupManager;

    /**
     * 即时消息发送器
     */
    private final ImmediateMessageSender immediateMessageSender;

    public WebSocketHandler(ChannelManager channelManager, GroupManager groupManager, ImmediateMessageSender immediateMessageSender) {
        this.channelManager = channelManager;
        this.groupManager = groupManager;
        this.immediateMessageSender = immediateMessageSender;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        ImmediateMessage msg = JsonUtils.fromJson(frame.text(), ImmediateMessage.class);
        MessageType messageType = msg.getMessage().getType();
        if (MessageType.PING.match(messageType)) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(
                ImmediateMessage.Message.builder()
                    .type(MessageType.PONG)
                    .build()
                    .toString())
            );
            return;
        }
        Channel channel = ctx.channel();
        String userId = channel.attr(AttributeKey.<String>valueOf("userId")).get();
        if (StrUtil.isBlank(userId)) {
            userId = msg.getFrom();
        }
        if (MessageType.JOIN_GROUP.match(messageType)) {
            this.groupManager.joinGroup(msg.getTo(), userId);
            return;
        }
        this.channelManager.add(userId, channel);
        this.immediateMessageSender.send(msg);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        this.channelManager.remove(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.READER_IDLE) {
                log.info("心跳超时，关闭{}连接", ctx.channel().id());
                this.channelManager.remove(ctx.channel());
                ctx.close();
            }
        }
    }

}
