package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowDefinition;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.enumeration.FlowDefinitionState;
import com.ale.starblog.framework.workflow.dao.mybatis.mapper.FlowDefinitionMapper;
import com.ale.starblog.framework.workflow.query.DefinitionQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 基于MybatisPlus的流程定义查询构建器
 *
 * @author Ale
 * @version 1.0.0 2025/7/18 10:09
 */
public class MybarisPlusDefinitionQuery extends AbstractMybatisPlusSortableQuery<FlowDefinition> implements DefinitionQuery {

    /**
     * 可排序的字段函数映射
     */
    private static final Map<String, SFunction<FlowDefinition, ?>> SORTABLE_FIELD_FUNCTION_MAPPING = Maps.newHashMap();

    static {
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.id, FlowDefinition::getId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.tenantId, FlowDefinition::getTenantId);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowEntity.Fields.createdAt, FlowDefinition::getCreatedAt);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowDefinition.Fields.sort, FlowDefinition::getSort);
        SORTABLE_FIELD_FUNCTION_MAPPING.put(FlowDefinition.Fields.version, FlowDefinition::getVersion);
    }

    /**
     * 流程定义Mapper
     */
    private final FlowDefinitionMapper definitionMapper;

    public MybarisPlusDefinitionQuery(FlowDefinitionMapper definitionMapper) {
        this.definitionMapper = definitionMapper;
    }

    /**
     * 流程定义ID
     */
    private String id;

    /**
     * 机构ID
     */
    private String tenantId;

    /**
     * 流程定义Key
     */
    private String definitionKey;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 是否发布
     */
    private Boolean published;

    /**
     * 状态
     */
    private FlowDefinitionState state;


    @Override
    public DefinitionQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public DefinitionQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public DefinitionQuery definitionKey(String definitionKey) {
        this.definitionKey = definitionKey;
        return this;
    }

    @Override
    public DefinitionQuery businessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    @Override
    public DefinitionQuery nameLike(String name) {
        this.name = name;
        return this;
    }

    @Override
    public DefinitionQuery published(Boolean published) {
        this.published = published;
        return this;
    }

    @Override
    public DefinitionQuery state(FlowDefinitionState state) {
        this.state = state;
        return this;
    }

    @Override
    protected SFunction<FlowDefinition, ?> provideSortFieldFunction(String field) {
        return SORTABLE_FIELD_FUNCTION_MAPPING.get(field);
    }

    @Override
    protected void executeBuildWrapper(LambdaQueryWrapper<FlowDefinition> queryWrapper) {
        queryWrapper.eq(FlowDefinition::getDeleted, false)
            .eq(StrUtil.isNotBlank(this.id), FlowDefinition::getId, this.id)
            .eq(StrUtil.isNotBlank(this.tenantId), FlowDefinition::getTenantId, this.tenantId)
            .eq(StrUtil.isNotBlank(this.definitionKey), FlowDefinition::getDefinitionKey, this.definitionKey)
            .eq(StrUtil.isNotBlank(this.businessType), FlowDefinition::getBusinessType, this.businessType)
            .like(StrUtil.isNotBlank(this.name), FlowDefinition::getName, this.name)
            .eq(this.published != null, FlowDefinition::getPublished, this.published);
        if (this.state != null) {
            queryWrapper.eq(FlowDefinition::getState, this.state.getValue());
        }
    }

    @Override
    protected BaseMapper<FlowDefinition> provideMapper() {
        return this.definitionMapper;
    }
}
