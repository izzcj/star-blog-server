package com.ale.starblog.framework.core.im.netty;

import io.netty.channel.Channel;
import java.util.Collection;

/**
 * 通道管理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 11:01
 */
public interface ChannelManager {

    /**
     * 添加通道
     *
     * @param userId  用户id
     * @param channel 通道
     */
    void add(String userId, Channel channel);

    /**
     * 获取通道
     *
     * @param userId 用户id
     * @return {@link Channel}
     */
    Channel get(String userId);

    /**
     * 获取所有UserId
     *
     * @return 通道集合
     */
    Collection<String> allUserIds();

    /**
     * 移除通道
     *
     * @param channel 通道
     */
    void remove(Channel channel);
}
