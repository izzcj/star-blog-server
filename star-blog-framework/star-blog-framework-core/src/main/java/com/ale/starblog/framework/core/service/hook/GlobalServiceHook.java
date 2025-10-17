package com.ale.starblog.framework.core.service.hook;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;

/**
 * 全局Service钩子
 *
 * @param <E> 实体类型
 * @author Ale
 * @version 1.0.0 2025/10/15 10:43
 */
public interface GlobalServiceHook<E extends BaseEntity> extends ServiceHook<E> {
}
