package com.ale.starblog.framework.core.im.netty;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.core.im.GroupManager;
import com.ale.starblog.framework.core.im.InstantMessage;
import com.ale.starblog.framework.core.im.InstantMessageSender;
import com.ale.starblog.framework.core.im.InstantMessageType;
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
    private final InstantMessageSender instantMessageSender;

    public WebSocketHandler(ChannelManager channelManager, GroupManager groupManager, InstantMessageSender instantMessageSender) {
        this.channelManager = channelManager;
        this.groupManager = groupManager;
        this.instantMessageSender = instantMessageSender;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        InstantMessage message = JsonUtils.fromJson(frame.text(), InstantMessage.class);
        InstantMessageType instantMessageType = message.getType();
        if (InstantMessageType.PING.match(instantMessageType)) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(
                JsonUtils.toJson(
                    InstantMessage.builder()
                        .type(InstantMessageType.PONG)
                        .build())
                )
            );
            return;
        }
        Channel channel = ctx.channel();
        String userId = channel.attr(AttributeKey.<String>valueOf("userId")).get();
        if (StrUtil.isBlank(userId)) {
            userId = message.getFrom();
        }
        if (InstantMessageType.JOIN_GROUP.match(instantMessageType)) {
            this.groupManager.joinGroup(message.getTo(), userId);
            this.channelManager.add(userId, channel);
            return;
        }
        this.instantMessageSender.send(message);
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
