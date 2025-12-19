package com.ale.starblog.framework.core.im.netty;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单通道管理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 10:21
 */
public class SimpleChannelManager implements ChannelManager {

    /**
     * 通道映射
     */
    private static final ConcurrentHashMap<String, Channel> CHANNEL_MAPPING = new ConcurrentHashMap<>();


    @Override
    public void add(String userId, Channel channel) {
        Channel old = CHANNEL_MAPPING.put(userId, channel);
        if (old != null && old != channel) {
            old.close();
        }
    }

    @Override
    public Channel get(String userId) {
        return CHANNEL_MAPPING.get(userId);
    }

    @Override
    public Collection<Channel> all() {
        return CHANNEL_MAPPING.values();
    }

    @Override
    public void remove(Channel channel) {
        CHANNEL_MAPPING.values().remove(channel);
    }

}
