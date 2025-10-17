package com.ale.starblog.framework.core.service.hook;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;

/**
 * 局部service钩子
 *
 * @param <E> 实体类型
 * @author Ale
 * @version 1.0.0 2025/10/15 10:41
 */
public interface LocalServiceHook<E extends BaseEntity> extends ServiceHook<E> {
}
