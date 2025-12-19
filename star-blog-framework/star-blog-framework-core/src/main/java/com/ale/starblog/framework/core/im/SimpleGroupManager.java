package com.ale.starblog.framework.core.im;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单群组管理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 17:22
 */
public class SimpleGroupManager implements GroupManager {

    /**
     * 群组映射
     */
    private static final ConcurrentHashMap<String, Collection<String>> GROUP_MAPPING = new ConcurrentHashMap<>();


    @Override
    public void joinGroup(String groupId, String userId) {
        GROUP_MAPPING.computeIfAbsent(groupId, k -> ConcurrentHashMap.newKeySet())
            .add(userId);
    }

    @Override
    public Collection<String> getGroupMembers(String groupId) {
        return GROUP_MAPPING.get(groupId);
    }

    @Override
    public void leaveGroup(String groupId, String userId) {
        GROUP_MAPPING.computeIfPresent(groupId, (k, v) -> {
            v.remove(userId);
            return v;
        });
    }
}
