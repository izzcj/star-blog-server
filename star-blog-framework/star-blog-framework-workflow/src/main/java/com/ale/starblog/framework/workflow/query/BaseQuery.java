package com.ale.starblog.framework.workflow.query;

import com.ale.starblog.framework.workflow.entity.FlowEntity;

import java.util.Collection;

/**
 * 查询基类接口
 *
 * @param <T> 查询结果类型
 * @author Ale
 * @version 1.0.0 2025/8/25 14:10
 */
public interface BaseQuery<T extends FlowEntity> extends SortableQuery<T> {

    /**
     * id查询
     *
     * @param id id
     * @return this
     */
    BaseQuery<T> id(String id);

    /**
     * id集合查询
     *
     * @param ids id集合
     * @return this
     */
    BaseQuery<T> ids(Collection<String> ids);

    /**
     * 机构id查询
     *
     * @param tenantId 机构id
     * @return this
     */
    BaseQuery<T> tenantId(String tenantId);

}
