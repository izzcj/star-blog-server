package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskState;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskType;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowTaskMapper;
import com.ale.starblog.framework.workflow.query.ActiveTaskQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基于MyBatisPlus的流程任务查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/18 10:31
 */
public class MybatisPlusActiveTaskQuery extends AbstractMybatisPlusSortableQuery<FlowTask> implements ActiveTaskQuery {

    /**
     * 可排序的字段函数映射
     */
    private static final Map<String, SFunction<FlowTask, ?>> SORTABLE_FIELD_FUNCTION_MAPPING = Maps.newHashMap();

    static {
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.id, FlowTask::getId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.tenantId, FlowTask::getTenantId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.createdAt, FlowTask::getCreatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowTask.Fields.instanceId, FlowTask::getInstanceId);
    }

    /**
     * 映射字段与函数的映射关系
     */
    private final FlowTaskMapper taskMapper;

    public MybatisPlusActiveTaskQuery(FlowTaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    /**
     * 任务id
     */
    private String id;

    /**
     * 机构ID
     */
    private String tenantId;

    /**
     * 实例id
     */
    private String instanceId;

    /**
     * 实例名称模糊匹配
     */
    private String instanceNameLike;

    /**
     * 创建时间大于等于
     */
    private LocalDateTime createdAtGe;

    /**
     * 创建时间小于等于
     */
    private LocalDateTime createdAtLe;

    /**
     * 任务名称模糊匹配
     */
    private String nameLike;

    /**
     * 任务状态
     */
    private FlowTaskState state;

    /**
     * 任务受理人
     */
    private String assigneeId;

    /**
     * 任务类型
     */
    private FlowTaskType type;

    @Override
    public ActiveTaskQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ActiveTaskQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public ActiveTaskQuery instanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    @Override
    public ActiveTaskQuery instanceNameLike(String instanceName) {
        this.instanceNameLike = instanceName;
        return this;
    }

    @Override
    public ActiveTaskQuery createdAtGe(LocalDateTime createdAt) {
        this.createdAtGe = createdAt;
        return this;
    }

    @Override
    public ActiveTaskQuery createdAtLe(LocalDateTime createdAt) {
        this.createdAtLe = createdAt;
        return this;
    }

    @Override
    public ActiveTaskQuery nameLike(String name) {
        this.nameLike = name;
        return this;
    }

    @Override
    public ActiveTaskQuery state(FlowTaskState state) {
        this.state = state;
        return this;
    }

    @Override
    public ActiveTaskQuery assigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    @Override
    public ActiveTaskQuery type(FlowTaskType type) {
        this.type = type;
        return this;
    }

    @Override
    protected SFunction<FlowTask, ?> provideSortFieldFunction(String field) {
        return SORTABLE_FIELD_FUNCTION_MAPPING.get(field);
    }

    @Override
    protected void executeBuildWrapper(LambdaQueryWrapper<FlowTask> queryWrapper) {
        queryWrapper.eq(StrUtil.isNotBlank(this.id), FlowTask::getId, this.id)
            .eq(StrUtil.isNotBlank(this.tenantId), FlowTask::getTenantId, this.tenantId)
            .eq(StrUtil.isNotBlank(this.instanceId), FlowTask::getInstanceId, this.instanceId)
            .ge(createdAtGe != null, FlowTask::getCreatedAt, createdAtGe)
            .le(createdAtLe != null, FlowTask::getCreatedAt, createdAtLe)
            .eq(StrUtil.isNotBlank(this.nameLike), FlowTask::getName, this.nameLike)
            .eq(StrUtil.isNotBlank(this.assigneeId), FlowTask::getAssigneeId, this.assigneeId)
            .eq(this.type != null, FlowTask::getType, this.type);
        if (this.state == null) {
            this.state = FlowTaskState.ACTIVE;
        }
        queryWrapper.eq(FlowTask::getState, this.state.getValue());
        if (StrUtil.isNotBlank(this.instanceNameLike)) {
            queryWrapper.inSql(
                FlowTask::getInstanceId,
                "select i.id from flow_instance i where i.name like " + "'%" + this.instanceNameLike + "%'"
            );
        }
    }

    @Override
    protected BaseMapper<FlowTask> provideMapper() {
        return this.taskMapper;
    }
}
