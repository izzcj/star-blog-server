package com.ale.starblog.framework.common.domain.entity;

/**
 * 实体初始化器
 *
 * @author Ale
 * @version 1.0.0 2025/8/5 09:35
 */
public interface EntityInitializer {

    /**
     * 初始化实体
     *
     * @param entityClass 实体类
     */
    void initialize(Class<? extends BaseEntity> entityClass);
}
