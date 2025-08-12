package com.ale.starblog.framework.workflow.query.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.workflow.exception.FlowException;
import com.ale.starblog.framework.workflow.query.SortableQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 基于MybatisPlus的抽象可排序查询构建器
 *
 * @param <T> 查询结果类型
 * @author Ale
 * @version 1.0.0 2025/7/17 9:35
 */
public abstract class AbstractMybatisPlusSortableQuery<T> extends AbstractMybatisPlusQuery<T> implements SortableQuery<T> {



    /**
     * 排序字段映射
     */
    protected Map<SFunction<T, ?>, Boolean> sortFieldMapping;

    @Override
    public SortableQuery<T> orderBy(String sortField, boolean isDesc) {
        if (this.sortFieldMapping == null) {
            this.sortFieldMapping = Maps.newHashMap();
        }
        this.sortFieldMapping.put(this.getSortFieldFunction(sortField), isDesc);
        return this;
    }

    @Override
    public SortableQuery<T> orderByDesc(String sortField, String... sortFields) {
        if (this.sortFieldMapping == null) {
            this.sortFieldMapping = Maps.newHashMap();
        }
        this.sortFieldMapping.put(this.getSortFieldFunction(sortField), true);
        if (sortFields != null) {
            for (String field : sortFields) {
                this.sortFieldMapping.put(this.getSortFieldFunction(field), true);
            }
        }
        return this;
    }

    @Override
    public SortableQuery<T> orderByAsc(String sortField, String... sortFields) {
        if (this.sortFieldMapping == null) {
            this.sortFieldMapping = Maps.newHashMap();
        }
        this.sortFieldMapping.put(this.getSortFieldFunction(sortField), false);
        if (sortFields != null) {
            for (String field : sortFields) {
                this.sortFieldMapping.put(this.getSortFieldFunction(field), false);
            }
        }
        return this;
    }

    @Override
    protected Wrapper<T> buildWrapper() {
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
        this.executeBuildWrapper(queryWrapper);
        if (CollectionUtil.isNotEmpty(this.sortFieldMapping)) {
            this.sortFieldMapping.forEach((field, isDesc) -> {
                if (isDesc) {
                    queryWrapper.orderByDesc(field);
                } else {
                    queryWrapper.orderByAsc(field);
                }
            });
        }
        return queryWrapper;
    }

    /**
     * 提供排序字段映射
     *
     * @param field 字段
     * @return SFunction
     */
    protected abstract SFunction<T, ?> provideSortFieldFunction(String field);

    /**
     * 构建查询条件
     *
     * @param queryWrapper 查询条件
     */
    protected abstract void executeBuildWrapper(LambdaQueryWrapper<T> queryWrapper);

    /**
     * 获取排序字段映射
     *
     * @param field 字段
     * @return SFunction
     */
    private SFunction<T, ?> getSortFieldFunction(String field) {
        SFunction<T, ?> sortFieldFunction = this.provideSortFieldFunction(field);
        if (sortFieldFunction == null) {
            throw new FlowException("不支持的排序字段[{}]", field);
        }
        return sortFieldFunction;
    }
}
