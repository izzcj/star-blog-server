package com.ale.starblog.framework.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.common.utils.GenericTypeUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.pojo.BaseBO;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.QueryConditionResolver;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.ale.starblog.framework.core.service.hook.HookStage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 抽象CRUD服务实现类
 *
 * @param <Mapper> Mapper
 * @param <E>      实体类型
 * @param <B>      实体BO类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/7
 */
public abstract class AbstractCrudServiceImpl<Mapper extends BaseMapper<E>, E extends BaseEntity, B extends BaseBO> extends AbstractMybatisPlusCrudServiceImpl<Mapper, E> implements ICrudService<E, B> {

    /**
     * 实体类型
     */
    @Getter
    private final Class<E> entityClass = CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), AbstractCrudServiceImpl.class, 1));

    /**
     * BO类型
     */
    private final Class<B> boClass = CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), AbstractCrudServiceImpl.class, 2));

    @Override
    public B queryOne(BaseQuery query) {
        return this.queryOne(query, HookContext.newContext());
    }

    @Override
    public B queryOne(BaseQuery query, HookContext context) {
        LambdaQueryWrapper<E> queryWrapper = this.buildQueryWrapper(query, context);
        return BeanUtil.copyProperties(this.getOne(queryWrapper), this.boClass);
    }

    @Override
    public List<B> queryList(BaseQuery query) {
        return this.queryList(query, HookContext.newContext());
    }

    @Override
    public List<B> queryList(BaseQuery query, HookContext context) {
        LambdaQueryWrapper<E> queryWrapper = this.buildQueryWrapper(query, context);
        return BeanUtil.copyToList(this.baseMapper.selectList(queryWrapper), this.boClass);
    }

    @Override
    public IPage<B> queryPage(Pageable pageable, BaseQuery query) {
        return this.queryPage(pageable, query, HookContext.newContext());
    }

    @Override
    public IPage<B> queryPage(Pageable pageable, BaseQuery query, HookContext context) {
        LambdaQueryWrapper<E> queryWrapper = this.buildQueryWrapper(query, context);
        return this.executeQueryPage(pageable, queryWrapper, this.boClass);
    }

    @Override
    public <T> IPage<T> executeQueryPage(Pageable pageable, Wrapper<E> queryWrapper, Class<T> clazz) {
        IPage<E> page = this.baseMapper.selectPage(new Page<>(pageable.getPageNumber(), pageable.getPageSize()), queryWrapper);
        IPage<T> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        result.setRecords(BeanUtil.copyToList(page.getRecords(), clazz));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(B entityBO) {
        this.create(entityBO, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(B entityBO, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        hookContext.set(HookConstants.ENTITY_BO_KEY, entityBO);
        E entity = BeanUtil.copyProperties(entityBO, this.entityClass);
        try {
            this.executeServiceHooks(entity, HookStage.BEFORE_CREATE, hookContext);
            this.executeServiceHooks(entity, HookStage.BEFORE_SAVE, hookContext);
            this.baseMapper.insert(entity);
            this.executeServiceHooks(entity, HookStage.AFTER_CREATE, hookContext);
            this.executeServiceHooks(entity, HookStage.AFTER_SAVE, hookContext);
            this.executeServiceHooks(entity, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchCreate(List<B> entityBOList) {
        this.batchCreate(entityBOList, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchCreate(List<B> entityBOList, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        hookContext.set(HookConstants.ENTITY_BO_LIST_KEY, entityBOList);
        List<E> entityList = BeanUtil.copyToList(entityBOList, this.entityClass);
        try {
            this.executeServiceHooks(entityList, HookStage.BEFORE_BATCH_CREATE, hookContext);
            this.executeServiceHooks(entityList, HookStage.BEFORE_BATCH_SAVE, hookContext);
            this.baseMapper.insert(entityList);
            this.executeServiceHooks(entityList, HookStage.AFTER_BATCH_CREATE, hookContext);
            this.executeServiceHooks(entityList, HookStage.AFTER_BATCH_SAVE, hookContext);
            this.executeServiceHooks(entityList, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(B entityBO) {
        this.modify(entityBO, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(B entityBO, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        E entity = BeanUtil.copyProperties(entityBO, this.entityClass);

        E oldEntity = this.getById(entity.getId());
        if (oldEntity == null) {
            throw new ServiceException("修改实体失败！实体不存在：{}", entity.getId());
        }
        hookContext.set(HookConstants.ENTITY_BO_KEY, entityBO);
        hookContext.set(HookConstants.OLD_ENTITY_KEY, oldEntity);

        try {
            this.executeServiceHooks(entity, HookStage.BEFORE_MODIFY, hookContext);
            this.executeServiceHooks(entity, HookStage.BEFORE_SAVE, hookContext);
            this.baseMapper.updateById(entity);
            this.executeServiceHooks(entity, HookStage.AFTER_MODIFY, hookContext);
            this.executeServiceHooks(entity, HookStage.AFTER_SAVE, hookContext);
            this.executeServiceHooks(entity, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchModify(List<B> entityBOList) {
        this.batchModify(entityBOList, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchModify(List<B> entityBOList, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        Map<Long, B> entityBOMapping = entityBOList.stream()
            .collect(Collectors.toMap(B::getId, Function.identity()));
        hookContext.set(HookConstants.ENTITY_BO_MAP_KEY, entityBOMapping);
        List<E> entityList = BeanUtil.copyToList(entityBOList, this.entityClass);

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
        hookContext.set(HookConstants.OLD_ENTITY_MAP_KEY, oldEntityMapping);

        try {
            this.executeServiceHooks(entityList, HookStage.BEFORE_BATCH_MODIFY, hookContext);
            this.executeServiceHooks(entityList, HookStage.BEFORE_BATCH_SAVE, hookContext);
            this.baseMapper.updateById(entityList);
            this.executeServiceHooks(entityList, HookStage.AFTER_BATCH_MODIFY, hookContext);
            this.executeServiceHooks(entityList, HookStage.AFTER_BATCH_SAVE, hookContext);
            this.executeServiceHooks(entityList, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        this.delete(id, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        E entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("删除实体失败！实体不存在：{}", id);
        }
        try {
            this.executeServiceHooks(entity, HookStage.BEFORE_DELETE, hookContext);
            this.baseMapper.deleteById(id);
            this.executeServiceHooks(entity, HookStage.AFTER_DELETE, hookContext);
            this.executeServiceHooks(entity, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(List<Long> ids) {
        this.batchDelete(ids, HookContext.newContext());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(List<Long> ids, HookContext context) {
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        Set<Long> idSet = Sets.newHashSet(ids);
        List<E> entityList = this.listByIds(ids)
            .stream()
            .toList();
        for (Long id : ids) {
            if (!idSet.contains(id)) {
                throw new ServiceException("删除实体失败！实体不存在：{}", id);
            }
        }
        try {
            this.executeServiceHooks(entityList, HookStage.BEFORE_BATCH_DELETE, hookContext);
            this.baseMapper.deleteByIds(ids);
            this.executeServiceHooks(entityList, HookStage.AFTER_BATCH_DELETE, hookContext);
            this.executeServiceHooks(entityList, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
    }

    /**
     * 构建查询条件
     *
     * @param query   查询参数
     * @param context 上下文
     * @return 查询条件
     */
    private LambdaQueryWrapper<E> buildQueryWrapper(BaseQuery query, HookContext context) {
        LambdaQueryWrapper<E> queryWrapper = QueryConditionResolver.resolveLambda(query);
        HookContext hookContext = Objects.requireNonNullElse(context, this.createHookContext());
        try {
            this.executeServiceHooks(queryWrapper, HookStage.BEFORE_QUERY, hookContext);
            this.executeServiceHooks(context, HookStage.BEFORE_CLEAR_HOOK_CONTEXT, hookContext);
        } finally {
            if (context == null) {
                hookContext.clear();
            }
        }
        return queryWrapper;
    }
}
