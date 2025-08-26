package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.workflow.entity.FlowEntity;
import com.ale.starblog.framework.workflow.query.BaseQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Collection;

/**
 * 基于MybatisPlus的抽象基础查询构建器
 *
 * @param <T> 查询结果类型
 * @author Ale
 * @version 1.0.0 2025/8/25 14:16
 */
public abstract class AbstractMybatisPlusBaseQuery<T extends FlowEntity> extends AbstractMybatisPlusSortableQuery<T> implements BaseQuery<T> {

    /**
     * id
     */
    protected String id;

    /**
     * id集合
     */
    protected Collection<String> ids;

    /**
     * 机构ID
     */
    protected String tenantId;

    @Override
    public BaseQuery<T> id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public BaseQuery<T> ids(Collection<String> ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public BaseQuery<T> tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    protected void executeBuildWrapper(QueryWrapper<T> queryWrapper) {
        queryWrapper.eq(StrUtil.isNotBlank(this.id), StrUtil.toUnderlineCase(FlowEntity.Fields.id), this.id)
            .in(CollectionUtil.isNotEmpty(this.ids), StrUtil.toUnderlineCase(FlowEntity.Fields.id), this.ids)
            .eq(StrUtil.isNotBlank(this.tenantId), StrUtil.toUnderlineCase(FlowEntity.Fields.tenantId), this.tenantId);
    }
}
