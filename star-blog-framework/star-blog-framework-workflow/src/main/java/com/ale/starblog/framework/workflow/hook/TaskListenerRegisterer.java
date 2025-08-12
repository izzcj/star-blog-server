package com.ale.starblog.framework.workflow.hook;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 任务监听器注册器
 *
 * @author Ale
 * @version 1.0.0 2025/7/14 11:39
 */
public class TaskListenerRegisterer implements InitializingBean {

    /**
     * 任务监听器
     */
    private final ObjectProvider<TaskListener> taskListeners;

    public TaskListenerRegisterer(ObjectProvider<TaskListener> taskListenerObjectProvider) {
        this.taskListeners = taskListenerObjectProvider;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            MethodHandles.privateLookupIn(TaskEventPublisher.class, MethodHandles.lookup())
                .findStatic(TaskEventPublisher.class, "registerTaskListener", MethodType.methodType(void.class, ObjectProvider.class))
                .invoke(this.taskListeners);
        } catch (Throwable e) {
            throw new RuntimeException("注册流程任务监听器失败！");
        }
    }
}
