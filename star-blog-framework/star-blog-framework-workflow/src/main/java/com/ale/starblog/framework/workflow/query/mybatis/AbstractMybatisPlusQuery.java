package com.ale.starblog.framework.workflow.query.mybatis;

import com.ale.starblog.framework.workflow.query.Query;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 基于MybatisPlus的抽象查询构建器
 *
 * @param <T> 查询结果类型
 * @author Ale
 * @version 1.0.0 2025/7/16 17:43
 */
public abstract class AbstractMybatisPlusQuery<T> implements Query<T> {

    /**
     * 提供Mapper
     *
     * @return Mapper
     */
    protected abstract BaseMapper<T> provideMapper();

    /**
     * 构建wrapper
     *
     * @return wrapper
     */
    protected abstract Wrapper<T> buildWrapper();

    @Override
    public T single() {
        return this.provideMapper().selectOne(this.buildWrapper());
    }

    @Override
    public List<T> list() {
        return this.provideMapper().selectList(this.buildWrapper());
    }

    @Override
    public Page<T> page(Pageable pageable) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> historyTaskPage = this.provideMapper().selectPage(
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageable.getPageNumber() + 1, pageable.getPageSize()),
            this.buildWrapper()
        );
        return new PageImpl<>(historyTaskPage.getRecords(), pageable, historyTaskPage.getTotal());
    }
}
