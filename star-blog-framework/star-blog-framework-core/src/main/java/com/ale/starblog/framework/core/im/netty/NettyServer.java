package com.ale.starblog.framework.core.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * netty服务端
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:17
 */
@Slf4j
public class NettyServer implements InitializingBean {

    /**
     * WebSocket初始化器
     */
    private final WebSocketInitializer webSocketInitializer;

    public NettyServer(WebSocketInitializer webSocketInitializer) {
        this.webSocketInitializer = webSocketInitializer;
    }

    @Override
    public void afterPropertiesSet() {
        new Thread(this::start).start();
    }

    /**
     * 启动
     */
    private void start() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(this.webSocketInitializer);

            b.bind(8081).sync();
            log.info("Netty WebSocket started at 8081");
        } catch (Exception e) {
            log.error("Netty WebSocket start failed", e);
        }
    }

}
