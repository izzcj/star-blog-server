package com.ale.starblog.framework.core.service;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.porxy.ProxyResolvable;
import com.ale.starblog.framework.core.pojo.BaseBO;
import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 通用service接口
 *
 * @param <E> 实体类型
 * @param <B> 实体BO类型
 * @param <C> 创建实体DTO类型
 * @param <M> 修改实体DTO类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/7
 */
public interface IBaseService<E extends BaseEntity, B extends BaseBO, C extends BaseCreateDTO, M extends BaseModifyDTO> extends IService<E>, ServerHook<E>, ProxyResolvable<IBaseService<E, B, C, M>> {

    /**
     * 根据查询条件查询单个实体
     *
     * @param query 查询条件
     * @return 实体
     */
    B queryOne(BaseQuery query);

    /**
     * 根据查询条件查询单个实体
     *
     * @param query   查询条件
     * @param context 上下文
     * @return 实体
     */
    B queryOne(BaseQuery query, HookContext context);

    /**
     * 根据查询条件查询实体列表
     *
     * @param query 查询条件
     * @return 实体列表
     */
    List<B> queryList(BaseQuery query);

    /**
     * 根据查询条件查询实体列表
     *
     * @param query   查询条件
     * @param context 上下文
     * @return 实体列表
     */
    List<B> queryList(BaseQuery query, HookContext context);

    /**
     * 根据查询条件查询分页实体
     *
     * @param pageable 分页信息
     * @param query    查询条件
     * @return 分页实体
     */
    IPage<B> queryPage(Pageable pageable, BaseQuery query);

    /**
     * 根据查询条件查询分页实体
     *
     * @param pageable 分页信息
     * @param query    查询条件
     * @param context  上下文
     * @return 分页实体
     */
    IPage<B> queryPage(Pageable pageable, BaseQuery query, HookContext context);

    /**
     * 执行分页查询
     *
     * @param <T>          返回类型
     * @param pageable     分页信息
     * @param queryWrapper 查询条件
     * @param clazz        实体类型
     * @return 分页实体
     */
    <T> IPage<T> executeQueryPage(Pageable pageable, Wrapper<E> queryWrapper, Class<T> clazz);

    /**
     * 创建实体
     *
     * @param entity 实体
     */
    void create(C entity);

    /**
     * 创建实体
     *
     * @param entity     实体
     * @param context    上下文
     */
    void create(C entity, HookContext context);

    /**
     * 批量创建实体
     *
     * @param entityList 实体列表
     */
    void batchCreate(List<C> entityList);

    /**
     * 批量创建实体
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    void batchCreate(List<C> entityList, HookContext context);

    /**
     * 修改实体
     *
     * @param entity 实体
     */
    void modify(M entity);

    /**
     * 修改实体
     *
     * @param entity  实体
     * @param context 上下文
     */
    void modify(M entity, HookContext context);

    /**
     * 批量修改实体
     *
     * @param entityList 实体列表
     */
    void batchModify(List<M> entityList);

    /**
     * 批量修改实体
     *
     * @param entityList 实体列表
     * @param context    上下文
     */
    void batchModify(List<M> entityList, HookContext context);

    /**
     * 根据id删除实体
     *
     * @param id 实体id
     */
    void delete(Long id);

    /**
     * 根据id删除实体
     *
     * @param id     实体id
     * @param context 上下文
     */
    void delete(Long id, HookContext context);

    /**
     * 批量删除实体
     *
     * @param ids 实体id列表
     */
    void batchDelete(List<Long> ids);

    /**
     * 批量删除实体
     *
     * @param ids    实体id列表
     * @param context 上下文
     */
    void batchDelete(List<Long> ids, HookContext context);
}
