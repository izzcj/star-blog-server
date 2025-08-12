package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.entity.FlowHistoryTask;
import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskState;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowHistoryTaskMapper;
import com.ale.starblog.framework.workflow.query.HistoryTaskQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基于MyBatisPlus的历史流程任务查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/16 17:05
 */
public class MybatisPlusHistoryTaskQuery extends AbstractMybatisPlusSortableQuery<FlowHistoryTask> implements HistoryTaskQuery {

    /**
     * 可排序的字段函数映射
     */
    private static final Map<String, SFunction<FlowHistoryTask, ?>> SORTABLE_FIELD_FUNCTION_MAPPING = Maps.newHashMap();

    static {
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.id, FlowHistoryTask::getId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.tenantId, FlowHistoryTask::getTenantId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.createdAt, FlowHistoryTask::getCreatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowTask.Fields.instanceId, FlowHistoryTask::getInstanceId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowTask.Fields.state, FlowHistoryTask::getState);
    }

    /**
     * 历史流程任务查询构建器
     */
    private final FlowHistoryTaskMapper historyTaskMapper;

    public MybatisPlusHistoryTaskQuery(FlowHistoryTaskMapper historyTaskMapper) {
        this.historyTaskMapper = historyTaskMapper;
    }

    /**
     * 流程任务ID
     */
    private String id;

    /**
     * 机构ID
     */
    private String tenantId;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 流程实例名称
     */
    private String instanceName;

    /**
     * 创建时间大于等于
     */
    private LocalDateTime createdAtGe;

    /**
     * 创建时间小于等于
     */
    private LocalDateTime createdAtLe;

    /**
     * 任务状态
     */
    private FlowTaskState state;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务处理人
     */
    private String assigneeId;

    @Override
    public HistoryTaskQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public HistoryTaskQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public HistoryTaskQuery instanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    @Override
    public HistoryTaskQuery instanceNameLike(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    @Override
    public HistoryTaskQuery createdAtGe(LocalDateTime createdAt) {
        this.createdAtGe = createdAt;
        return this;
    }

    @Override
    public HistoryTaskQuery createdAtLe(LocalDateTime createdAt) {
        this.createdAtLe = createdAt;
        return this;
    }

    @Override
    public HistoryTaskQuery state(FlowTaskState state) {
        this.state = state;
        return this;
    }

    @Override
    public HistoryTaskQuery nameLike(String name) {
        this.name = name;
        return this;
    }

    @Override
    public HistoryTaskQuery assigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    @Override
    protected SFunction<FlowHistoryTask, ?> provideSortFieldFunction(String field) {
        return SORTABLE_FIELD_FUNCTION_MAPPING.get(field);
    }

    /**
     * 构建wrapper
     */
    @Override
    protected void executeBuildWrapper(LambdaQueryWrapper<FlowHistoryTask> queryWrapper) {
        queryWrapper.eq(FlowHistoryTask::getDeleted, false)
            .eq(StrUtil.isNotBlank(this.id), FlowHistoryTask::getId, this.id)
            .eq(StrUtil.isNotBlank(this.tenantId), FlowHistoryTask::getTenantId, this.tenantId)
            .eq(StrUtil.isNotBlank(this.instanceId), FlowHistoryTask::getInstanceId, this.instanceId)
            .ge(this.createdAtGe != null, FlowHistoryTask::getCreatedAt, this.createdAtGe)
            .le(this.createdAtLe != null, FlowHistoryTask::getCreatedAt, this.createdAtLe)
            .eq(StrUtil.isNotBlank(this.name), FlowHistoryTask::getName, this.name)
            .eq(StrUtil.isNotBlank(this.assigneeId), FlowHistoryTask::getAssigneeId, this.assigneeId);
        if (StrUtil.isNotBlank(this.instanceName)) {
            queryWrapper.inSql(
                FlowHistoryTask::getInstanceId,
                "select hi.id from flow_history_instance hi where hi.name like " + "'%" + this.instanceName + "%'"
            );
        }
        if (this.state != null) {
            queryWrapper.eq(FlowHistoryTask::getState, this.state.getValue());
        }
    }

    @Override
    protected BaseMapper<FlowHistoryTask> provideMapper() {
        return this.historyTaskMapper;
    }
}
