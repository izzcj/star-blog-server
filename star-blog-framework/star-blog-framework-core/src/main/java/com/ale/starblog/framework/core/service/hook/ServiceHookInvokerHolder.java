package com.ale.starblog.framework.core.service.hook;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.support.TriConsumer;

/**
 * service钩子调用器持有器
 *
 * @param <E> 实体类型
 * @author Ale
 * @version 1.0.0 2025/10/15 15:35
 */
public interface ServiceHookInvokerHolder<E extends BaseEntity> extends HookInvokerHolder<TriConsumer<GlobalServiceHook<E>, ?, HookContext>, TriConsumer<LocalServiceHook<E>, ?, HookContext>> {
}
