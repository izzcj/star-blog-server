package com.ale.starblog.framework.workflow.hook;

import com.ale.starblog.framework.common.support.Supportable;
import com.ale.starblog.framework.workflow.entity.FlowInstance;

/**
 * 流程实例监听器
 *
 * @author Ale
 * @version 1.0.0 2025/6/12 15:50
 */
public interface InstanceListener extends Supportable<InstanceEventType> {

    /**
     * 流程实例事件通知
     *
     * @param eventType    事件类型
     * @param flowInstance 流程实例
     * @return 是否处理成功
     */
    boolean notify(InstanceEventType eventType, FlowInstance flowInstance);

}
