package com.ale.starblog.framework.core.service.hook;

/**
 * 钩子回调器持有器
 *
 * @param <G> 全局钩子
 * @param <L> 局部钩子
 * @author Ale
 * @version 1.0.0 2025/10/15 15:19
 */
public interface HookInvokerHolder<G, L> {

    /**
     * 获取全局钩子调用器
     *
     * @param hookStage 钩子阶段
     * @return 调用器
     */
    G getGlobalHookInvoker(HookStage hookStage);

    /**
     * 获取局部钩子调用器
     *
     * @param hookStage 钩子阶段
     * @return 调用器
     */
    L getLocalHookInvoker(HookStage hookStage);

}
