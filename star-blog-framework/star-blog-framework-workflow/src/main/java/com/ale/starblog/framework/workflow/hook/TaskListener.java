package com.ale.starblog.framework.workflow.hook;

import com.ale.starblog.framework.common.support.Supportable;
import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.entity.FlowTaskActor;

import java.util.List;

/**
 * 流程任务监听器
 *
 * @author Ale
 * @version 1.0.0 2025/6/13 10:11
 */
public interface TaskListener extends Supportable<TaskEventType> {

    /**
     * 流程任务事件通知
     *
     * @param eventType  事件类型
     * @param flowTask   流程任务
     * @param taskActors 流程任务参与人
     * @return 是否处理成功
     */
    boolean notify(TaskEventType eventType, FlowTask flowTask, List<FlowTaskActor> taskActors);

}
