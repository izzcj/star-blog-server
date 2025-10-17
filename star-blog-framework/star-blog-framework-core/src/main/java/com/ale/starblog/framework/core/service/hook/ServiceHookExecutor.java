package com.ale.starblog.framework.core.service.hook;

/**
 * Service钩子执行器
 *
 * @author Ale
 * @version 1.0.0 2025/10/15 15:31
 */
public interface ServiceHookExecutor extends HookExecutor {

    /**
     * 执行全局ServiceHook
     *
     * @param param       参数
     * @param hookStage   钩子阶段
     * @param hookContext 钩子上下文
     */
    void executeGlobalServiceHook(Object param, HookStage hookStage, HookContext hookContext);

    /**
     * 执行局部ServiceHook
     *
     * @param param       参数
     * @param hookStage   钩子阶段
     * @param hookContext 钩子上下文
     */
    void executeLocalServiceHook(Object param, HookStage hookStage, HookContext hookContext);

    /**
     * 执行ServiceHook
     *
     * @param param       参数
     * @param hookStage   钩子阶段
     * @param hookContext 钩子上下文
     */
    void executeServiceHooks(Object param, HookStage hookStage, HookContext hookContext);

}
