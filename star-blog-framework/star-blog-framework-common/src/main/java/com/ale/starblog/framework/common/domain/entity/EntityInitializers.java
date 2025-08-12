package com.ale.starblog.framework.common.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 实体初始化器集合
 *
 * @author Ale
 * @version 1.0.0 2025/8/5 09:36
 */
@Getter
@RequiredArgsConstructor
@Component("entityInitializers")
public class EntityInitializers {

    /**
     * 实体初始化器
     */
    private final ObjectProvider<EntityInitializer> initializers;
}
