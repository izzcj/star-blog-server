package com.ale.starblog.framework.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.common.utils.GenericTypeUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.pojo.BaseBO;
import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.QueryConditionResolver;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用service接口实现类
 *
 * @param <Mapper> Mapper
 * @param <E> 实体类型
 * @param <B> 实体BO类型
 * @param <C> 创建实体DTO类型
 * @param <M> 修改实体DTO类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/7
 */
public class BaseServiceImpl<Mapper extends BaseMapper<E>, E extends BaseEntity, B extends BaseBO, C extends BaseCreateDTO, M extends BaseModifyDTO> extends ServiceImpl<Mapper, E> implements IBaseService<E, B, C, M> {

    /**
     * 实体类型
     */
    @Getter
    private final Class<E> entityClass = CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), BaseServiceImpl.class, 1));

    /**
     * BO类型
     */
    private final Class<B> boClass = CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), BaseServiceImpl.class, 2));

    @Override
    public B queryOne(BaseQuery query) {
        return this.queryOne(query, new HookContext());
    }

    @Override
    public B queryOne(BaseQuery query, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        LambdaQueryWrapper<E> queryWrapper = QueryConditionResolver.resolveLambda(query);
        this.beforeQuery(queryWrapper, context);
        this.clearHookContext(context);
        return BeanUtil.copyProperties(this.getOne(queryWrapper), this.boClass);
    }

    @Override
    public List<B> queryList(BaseQuery query) {
        return this.queryList(query, new HookContext());
    }

    @Override
    public List<B> queryList(BaseQuery query, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        LambdaQueryWrapper<E> queryWrapper = QueryConditionResolver.resolveLambda(query);
        this.beforeQuery(queryWrapper, context);
        this.clearHookContext(context);
        return BeanUtil.copyToList(this.list(queryWrapper), this.boClass);
    }

    @Override
    public IPage<B> queryPage(Pageable pageable, BaseQuery query) {
        return this.queryPage(pageable, query, new HookContext());
    }

    @Override
    public IPage<B> queryPage(Pageable pageable, BaseQuery query, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        LambdaQueryWrapper<E> queryWrapper = QueryConditionResolver.resolveLambda(query);
        this.beforeQuery(queryWrapper, context);

        IPage<B> result = this.executeQueryPage(pageable, queryWrapper, this.boClass);

        this.clearHookContext(context);
        return result;
    }

    @Override
    public <T> IPage<T> executeQueryPage(Pageable pageable, Wrapper<E> queryWrapper, Class<T> clazz) {
        IPage<E> page = this.page(new Page<>(pageable.getPageNumber(), pageable.getPageSize()), queryWrapper);
        IPage<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(BeanUtil.copyToList(page.getRecords(), clazz));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(C createEntityDTO) {
        this.create(createEntityDTO, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(C createEntityDTO, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        E entity = BeanUtil.copyProperties(createEntityDTO, this.entityClass);
        this.beforeCreate(entity, context);
        this.beforeSave(entity, context);
        this.save(entity);
        this.afterCreate(entity, context);
        this.afterSave(entity, context);

        this.clearHookContext(context);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchCreate(List<C> createEntityDTOList) {
        this.batchCreate(createEntityDTOList, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchCreate(List<C> createEntityDTOList, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        List<E> entityList = BeanUtil.copyToList(createEntityDTOList, this.entityClass);

        this.beforeBatchSave(entityList, context);
        this.beforeBatchCreate(entityList, context);
        this.saveBatch(entityList);
        this.afterBatchCreate(entityList, context);
        this.afterBatchSave(entityList, context);

        this.clearHookContext(context);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(M modifyEntityDTO) {
        this.modify(modifyEntityDTO, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(M modifyEntityDTO, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        E entity = BeanUtil.copyProperties(modifyEntityDTO, this.entityClass);

        E oldEntity = this.getById(entity.getId());
        if (oldEntity == null) {
            throw new ServiceException("修改实体失败！实体不存在：{}", entity.getId());
        }
        context.set(HookConstants.OLD_ENTITY_KEY, oldEntity);

        this.beforeSave(entity, context);
        this.beforeModify(entity, context);
        this.updateById(entity);
        this.afterModify(entity, context);
        this.afterSave(entity, context);

        this.clearHookContext(context);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchModify(List<M> modifyEntityDTO) {
        this.batchModify(modifyEntityDTO, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchModify(List<M> modifyEntityDTO, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        List<E> entityList = BeanUtil.copyToList(modifyEntityDTO, this.entityClass);

        List<Long> ids = entityList.stream()
            .map(E::getId)
            .toList();

        Map<Long, E> oldEntityMapping = this.listByIds(ids)
            .stream()
            .collect(Collectors.toMap(E::getId, Function.identity()));
        for (Long id : ids) {
            if (!oldEntityMapping.containsKey(id)) {
                throw new ServiceException("修改实体失败！实体不存在：{}", id);
            }
        }
        context.set(HookConstants.OLD_ENTITY_MAP_KEY, oldEntityMapping);

        this.beforeBatchSave(entityList, context);
        this.beforeBatchModify(entityList, context);
        this.updateBatchById(entityList);
        this.afterBatchModify(entityList, context);
        this.afterBatchSave(entityList, context);

        this.clearHookContext(context);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        this.delete(id, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        E entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("删除实体失败！实体不存在：{}", id);
        }
        context.set(HookConstants.OLD_ENTITY_KEY, entity);
        this.beforeDelete(id, context);
        this.removeById(id);
        this.afterDelete(id, context);

        this.clearHookContext(context);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(List<Long> ids) {
        this.batchDelete(ids, new HookContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(List<Long> ids, HookContext context) {
        if (context == null) {
            context = new HookContext();
        }
        Map<Long, E> entityMap = this.listByIds(ids)
            .stream()
            .collect(Collectors.toMap(E::getId, Function.identity()));
        for (Long id : ids) {
            if (!entityMap.containsKey(id)) {
                throw new ServiceException("删除实体失败！实体不存在：{}", id);
            }
        }
        context.set(HookConstants.OLD_ENTITY_MAP_KEY, entityMap);
        this.beforeBatchDelete(ids, context);
        this.removeByIds(ids);
        this.afterBatchDelete(ids, context);

        this.clearHookContext(context);
    }

    /**
     * 清除上下文
     *
     * @param context 上文文
     */
    private void clearHookContext(HookContext context) {
        context.clear();
    }
}
