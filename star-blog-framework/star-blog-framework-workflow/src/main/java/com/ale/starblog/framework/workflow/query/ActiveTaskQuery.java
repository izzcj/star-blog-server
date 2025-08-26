package com.ale.starblog.framework.workflow.query;

import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskType;

import java.time.LocalDateTime;

/**
 * 流程任务查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/16 18:01
 */
public interface ActiveTaskQuery extends BaseQuery<FlowTask> {

    /**
     * 流程实例ID
     *
     * @param instanceId 流程实例ID
     * @return this
     */
    ActiveTaskQuery instanceId(String instanceId);

    /**
     * 流程实例名称模糊匹配
     *
     * @param instanceName 实例名称
     * @return this
     */
    ActiveTaskQuery instanceNameLike(String instanceName);

    /**
     * 创建时间大于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    ActiveTaskQuery createdAtGe(LocalDateTime createdAt);

    /**
     * 创建时间小于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    ActiveTaskQuery createdAtLe(LocalDateTime createdAt);

    /**
     * 流程任务名称模糊匹配
     *
     * @param name 流程任务名称
     * @return this
     */
    ActiveTaskQuery nameLike(String name);

    /**
     * 受理人ID
     *
     * @param assigneeId 受理人ID
     * @return this
     */
    ActiveTaskQuery assigneeId(String assigneeId);

    /**
     * 任务类型
     *
     * @param type 任务类型
     * @return this
     */
    ActiveTaskQuery type(FlowTaskType type);

}
