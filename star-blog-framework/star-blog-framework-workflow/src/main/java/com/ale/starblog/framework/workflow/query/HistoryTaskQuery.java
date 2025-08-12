package com.ale.starblog.framework.workflow.query;

import com.ale.starblog.framework.workflow.entity.FlowHistoryTask;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskState;

import java.time.LocalDateTime;

/**
 * 历史流程任务查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/16 16:51
 */
public interface HistoryTaskQuery extends SortableQuery<FlowHistoryTask> {

    /**
     * 流程任务ID
     *
     * @param id 任务ID
     * @return this
     */
    HistoryTaskQuery id(String id);

    /**
     * 机构ID
     *
     * @param tenantId 机构ID
     * @return this
     */
    HistoryTaskQuery tenantId(String tenantId);

    /**
     * 流程实例ID
     *
     * @param instanceId 流程实例ID
     * @return this
     */
    HistoryTaskQuery instanceId(String instanceId);

    /**
     * 流程实例名称模糊匹配
     *
     * @param instanceName 实例名称
     * @return this
     */
    HistoryTaskQuery instanceNameLike(String instanceName);

    /**
     * 创建时间大于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    HistoryTaskQuery createdAtGe(LocalDateTime createdAt);

    /**
     * 创建时间小于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    HistoryTaskQuery createdAtLe(LocalDateTime createdAt);

    /**
     * 流程任务状态
     *
     * @param state 流程任务状态
     * @return this
     */
    HistoryTaskQuery state(FlowTaskState state);

    /**
     * 流程任务名称模糊匹配
     *
     * @param name 流程任务名称
     * @return this
     */
    HistoryTaskQuery nameLike(String name);

    /**
     * 受理人ID
     *
     * @param assigneeId 受理人ID
     * @return this
     */
    HistoryTaskQuery assigneeId(String assigneeId);

}
