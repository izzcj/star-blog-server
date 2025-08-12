package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.entity.FlowHistoryInstance;
import com.ale.starblog.framework.workflow.enumeration.FlowInstanceState;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowHistoryInstanceMapper;
import com.ale.starblog.framework.workflow.query.HistoryInstanceQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基于MybatisPlus的流程历史实例查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/15 17:02
 */
public class MybatisPlusHistoryInstanceQuery extends AbstractMybatisPlusSortableQuery<FlowHistoryInstance> implements HistoryInstanceQuery {

    /**
     * 可排序的字段函数映射
     */
    private static final Map<String, SFunction<FlowHistoryInstance, ?>> SORTABLE_FIELD_FUNCTION_MAPPING = Maps.newHashMap();

    static {
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.id, FlowHistoryInstance::getId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.tenantId, FlowHistoryInstance::getTenantId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.createdAt, FlowHistoryInstance::getCreatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.updatedAt, FlowHistoryInstance::getUpdatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowHistoryInstance.Fields.state, FlowHistoryInstance::getState);
    }

    /**
     * 流程历史实例Mapper
     */
    private final FlowHistoryInstanceMapper historyInstanceMapper;


    public MybatisPlusHistoryInstanceQuery(FlowHistoryInstanceMapper historyInstanceMapper) {
        this.historyInstanceMapper = historyInstanceMapper;
    }

    /**
     * 流程实例ID
     */
    private String id;

    /**
     * 机构ID
     */
    private String tenantId;

    /**
     * 流程发起人ID
     */
    private String starterId;

    /**
     * 受理人ID
     */
    private String assigneeId;

    /**
     * 创建时间大于等于
     */
    private LocalDateTime createdAtGe;

    /**
     * 创建时间小于等于
     */
    private LocalDateTime createdAtLe;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 流程实例状态
     */
    private FlowInstanceState state;

    /**
     * 标题模糊查询
     */
    private String titleLike;

    @Override
    public HistoryInstanceQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public HistoryInstanceQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public HistoryInstanceQuery starterId(String starterId) {
        this.starterId = starterId;
        return this;
    }

    @Override
    public HistoryInstanceQuery assigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    @Override
    public HistoryInstanceQuery createdAtGe(LocalDateTime createdAt) {
        this.createdAtGe = createdAt;
        return this;
    }

    @Override
    public HistoryInstanceQuery createdAtLe(LocalDateTime createdAt) {
        this.createdAtLe = createdAt;
        return this;
    }

    @Override
    public HistoryInstanceQuery businessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    @Override
    public HistoryInstanceQuery businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    @Override
    public HistoryInstanceQuery state(FlowInstanceState state) {
        this.state = state;
        return this;
    }

    @Override
    public HistoryInstanceQuery titleLike(String title) {
        this.titleLike = title;
        return this;
    }

    @Override
    protected SFunction<FlowHistoryInstance, ?> provideSortFieldFunction(String field) {
        return SORTABLE_FIELD_FUNCTION_MAPPING.get(field);
    }

    /**
     * 构建wrapper
     */
    @Override
    protected void executeBuildWrapper(LambdaQueryWrapper<FlowHistoryInstance> queryWrapper) {
        queryWrapper.eq(FlowHistoryInstance::getDeleted, false)
            .eq(StrUtil.isNotBlank(this.id), FlowHistoryInstance::getId, this.id)
            .eq(StrUtil.isNotBlank(this.tenantId), FlowHistoryInstance::getTenantId, this.tenantId)
            .eq(StrUtil.isNotBlank(this.starterId), FlowHistoryInstance::getCreatedBy, this.starterId)
            .ge(this.createdAtGe != null, FlowHistoryInstance::getCreatedAt, this.createdAtGe)
            .le(this.createdAtLe != null, FlowHistoryInstance::getCreatedAt, this.createdAtLe)
            .eq(StrUtil.isNotBlank(this.businessType), FlowHistoryInstance::getBusinessType, this.businessType)
            .eq(StrUtil.isNotBlank(this.businessId), FlowHistoryInstance::getBusinessId, this.businessId)
            .like(StrUtil.isNotBlank(this.titleLike), FlowHistoryInstance::getTitle, this.titleLike);
        if (this.state != null) {
            queryWrapper.eq(FlowHistoryInstance::getState, this.state.getValue());
        }
        if (StrUtil.isNotBlank(this.assigneeId)) {
            queryWrapper.exists(
                "select 1 from flow_history_task ht where ht.instance_id = flow_history_instance.id and ht.assignee_id = {0}",
                this.assigneeId
            );
        }
    }

    @Override
    protected BaseMapper<FlowHistoryInstance> provideMapper() {
        return this.historyInstanceMapper;
    }
}
