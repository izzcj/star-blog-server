package com.ale.starblog.framework.core.service;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;

/**
 * service钩子
 *
 * @param <E> 实体类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/26
 */
public interface ServerHook<E extends BaseEntity> {

    /**
     * 查询前置处理
     *
     * @param queryWrapper 查询参数
     * @param context      上下文
     */
    default void beforeQuery(LambdaQueryWrapper<E> queryWrapper, HookContext context) {
    }

    /**
     * 创建前置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void beforeCreate(E entity, HookContext context) {
    }

    /**
     * 创建后置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void afterCreate(E entity, HookContext context) {
    }

    /**
     * 批量创建前置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void beforeBatchCreate(List<E> entityList, HookContext context) {
    }

    /**
     * 批量创建后置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void afterBatchCreate(List<E> entityList, HookContext context) {
    }

    /**
     * 修改前置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void beforeModify(E entity, HookContext context) {
    }

    /**
     * 修改后置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void afterModify(E entity, HookContext context) {
    }

    /**
     * 批量修改前置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void beforeBatchModify(List<E> entityList, HookContext context) {
    }

    /**
     * 批量修改后置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void afterBatchModify(List<E> entityList, HookContext context) {
    }

    /**
     * 保存前置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void beforeSave(E entity, HookContext context) {
    }

    /**
     * 保存后置处理
     *
     * @param entity  实体
     * @param context 上下文
     */
    default void afterSave(E entity, HookContext context) {
    }

    /**
     * 批量保存前置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void beforeBatchSave(List<E> entityList, HookContext context) {
    }

    /**
     * 批量保存后置处理
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    default void afterBatchSave(List<E> entityList, HookContext context) {
    }

    /**
     * 删除前置处理
     *
     * @param id      ID
     * @param context 上下文
     */
    default void beforeDelete(Long id, HookContext context) {
    }

    /**
     * 删除后置处理
     *
     * @param id      ID
     * @param context 上下文
     */
    default void afterDelete(Long id, HookContext context) {
    }

    /**
     * 批量删除前置处理
     *
     * @param ids     ID列表
     * @param context 上下文
     */
    default void beforeBatchDelete(List<Long> ids, HookContext context) {
    }

    /**
     * 批量删除后置处理
     *
     * @param ids     ID列表
     * @param context 上下文
     */
    default void afterBatchDelete(List<Long> ids, HookContext context) {
    }
}
