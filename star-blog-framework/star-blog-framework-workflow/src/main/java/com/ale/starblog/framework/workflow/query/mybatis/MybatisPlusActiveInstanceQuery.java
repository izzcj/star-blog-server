package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowInstanceMapper;
import com.ale.starblog.framework.workflow.query.ActiveInstanceQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基于MybatisPlus的流程实例查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/18 10:22
 */
public class MybatisPlusActiveInstanceQuery extends AbstractMybatisPlusSortableQuery<FlowInstance> implements ActiveInstanceQuery {

    /**
     * 可排序的字段函数映射
     */
    private static final Map<String, SFunction<FlowInstance, ?>> SORTABLE_FIELD_FUNCTION_MAPPING = Maps.newHashMap();

    static {
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.id, FlowInstance::getId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.tenantId, FlowInstance::getTenantId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.createdAt, FlowInstance::getCreatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.updatedAt, FlowInstance::getUpdatedAt);
    }

    /**
     * 流程实例Mapper
     */
    private final FlowInstanceMapper instanceMapper;

    public MybatisPlusActiveInstanceQuery(FlowInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
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
     * 创建时间开始
     */
    private LocalDateTime createdAtGe;

    /**
     * 创建时间结束
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
     * 标题模糊匹配
     */
    private String titleLike;

    @Override
    public ActiveInstanceQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ActiveInstanceQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public ActiveInstanceQuery starterId(String starterId) {
        this.starterId = starterId;
        return this;
    }

    @Override
    public ActiveInstanceQuery createdAtGe(LocalDateTime createdAt) {
        this.createdAtGe = createdAt;
        return this;
    }

    @Override
    public ActiveInstanceQuery createdAtLe(LocalDateTime createdAt) {
        this.createdAtLe = createdAt;
        return this;
    }

    @Override
    public ActiveInstanceQuery businessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    @Override
    public ActiveInstanceQuery businessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    @Override
    public ActiveInstanceQuery titleLike(String title) {
        this.titleLike = title;
        return this;
    }

    @Override
    protected SFunction<FlowInstance, ?> provideSortFieldFunction(String field) {
        return SORTABLE_FIELD_FUNCTION_MAPPING.get(field);
    }

    @Override
    protected void executeBuildWrapper(LambdaQueryWrapper<FlowInstance> queryWrapper) {
        queryWrapper.eq(StrUtil.isNotBlank(this.id), FlowInstance::getId, this.id)
            .eq(StrUtil.isNotBlank(this.tenantId), FlowInstance::getTenantId, this.tenantId)
            .eq(StrUtil.isNotBlank(this.starterId), FlowInstance::getCreatedBy, this.starterId)
            .ge(this.createdAtGe != null, FlowInstance::getCreatedAt, this.createdAtGe)
            .le(this.createdAtLe != null, FlowInstance::getCreatedAt, this.createdAtLe)
            .eq(StrUtil.isNotBlank(this.businessType), FlowInstance::getBusinessType, this.businessType)
            .eq(StrUtil.isNotBlank(this.businessId), FlowInstance::getBusinessId, this.businessId)
            .like(StrUtil.isNotBlank(this.titleLike), FlowInstance::getTitle, this.titleLike);
    }

    @Override
    protected BaseMapper<FlowInstance> provideMapper() {
        return this.instanceMapper;
    }
}
