package com.ale.starblog.framework.workflow.query;


import com.ale.starblog.framework.workflow.entity.FlowHistoryInstance;
import com.ale.starblog.framework.workflow.enumeration.FlowInstanceState;

import java.time.LocalDateTime;

/**
 * 历史流程实例查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/15 14:39
 */
public interface HistoryInstanceQuery extends SortableQuery<FlowHistoryInstance> {

    /**
     * 流程实例ID
     *
     * @param id 流程实例ID
     * @return this
     */
    HistoryInstanceQuery id(String id);

    /**
     * 机构ID
     *
     * @param tenantId 机构ID
     * @return this
     */
    HistoryInstanceQuery tenantId(String tenantId);

    /**
     * 发起人
     *
     * @param starterId 发起人
     * @return this
     */
    HistoryInstanceQuery starterId(String starterId);

    /**
     * 受理人
     *
     * @param assigneeId 受理人
     * @return this
     */
    HistoryInstanceQuery assigneeId(String assigneeId);

    /**
     * 创建时间大于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    HistoryInstanceQuery createdAtGe(LocalDateTime createdAt);

    /**
     * 创建时间小于等于
     *
     * @param createdAt 创建时间
     * @return this
     */
    HistoryInstanceQuery createdAtLe(LocalDateTime createdAt);

    /**
     * 业务类型
     *
     * @param businessType 业务类型
     * @return this
     */
    HistoryInstanceQuery businessType(String businessType);

    /**
     * 业务ID
     *
     * @param businessId 业务ID
     * @return this
     */
    HistoryInstanceQuery businessId(String businessId);

    /**
     * 流程实例状态
     *
     * @param state 状态
     * @return this
     */
    HistoryInstanceQuery state(FlowInstanceState state);

    /**
     * 标题模糊插叙
     *
     * @param title 标题
     * @return this
     */
    HistoryInstanceQuery titleLike(String title);

}
