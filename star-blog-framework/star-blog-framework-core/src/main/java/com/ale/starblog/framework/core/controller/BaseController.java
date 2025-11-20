package com.ale.starblog.framework.core.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.common.utils.GenericTypeUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.pojo.BaseBO;
import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import com.ale.starblog.framework.core.pojo.BaseVO;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.ale.starblog.framework.core.service.ICrudService;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Controller基类
 *
 * @param <E> 实体类型
 * @param <S> Service类型
 * @param <V> VO类型
 * @param <B> BO类型
 * @param <C> 创建DTO类型
 * @param <M> 修改DTO类型
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Slf4j
public abstract class BaseController<E extends BaseEntity, S extends ICrudService<E, B>, V extends BaseVO, B extends BaseBO, C extends BaseCreateDTO, M extends BaseModifyDTO> {

    /**
     * Service
     * 使用setter方式注入或者@Autowired方式注入
     */
    protected S service;

    @Resource
    public void setService(S service) {
        this.service = service;
    }

    /**
     * BO类型
     */
    private final Class<B> boClass = this.deduceBoClass();

    /**
     * VO类型
     */
    private final Class<V> voClass = this.deduceVoClass();

    /**
     * 推断VO类型
     *
     * @return VO类型
     */
    private Class<V> deduceVoClass() {
        return CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), BaseController.class, 2));
    }

    /**
     * 推断BO类型
     *
     * @return BO类型
     */
    private Class<B> deduceBoClass() {
        return CastUtils.cast(GenericTypeUtils.resolveTypeArguments(this.getClass(), BaseController.class, 3));
    }

    /**
     * 根据ID查询对象
     *
     * @param id ID
     * @return 结果
     */
    protected JsonResult<V> queryById(Long id) {
        if (id == null) {
            return JsonResult.fail("id不能为空");
        }
        E entity = this.service.getById(id);
        if (entity == null) {
            return JsonResult.fail("查询对象失败失败，对象不存在！ID：" + id);
        }
        V result = BeanUtil.copyProperties(entity, this.voClass);
        this.translation(result);
        return JsonResult.success(result);
    }

    /**
     * 根据查询条件查询对象
     *
     * @param query 查询条件
     * @return 结果
     */
    protected JsonResult<V> queryOne(BaseQuery query) {
        B entityBO = this.service.queryOne(query);
        V result = BeanUtil.copyProperties(entityBO, this.voClass);
        this.translation(result);
        return JsonResult.success(result);
    }

    /**
     * 查询列表
     *
     * @param query 查询条件
     * @return 结果
     */
    protected JsonResult<List<V>> queryList(BaseQuery query) {
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.QUERY_KEY, query);
        List<B> boList = this.service.queryList(query, hookContext);
        List<V> result = BeanUtil.copyToList(boList, this.voClass);
        if (CollectionUtil.isNotEmpty(result)) {
            result.forEach(this::translation);
        }
        return JsonResult.success(result);
    }

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 结果
     */
    protected JsonResult<JsonPageResult.PageData<V>> queryPage(@PageableDefault(page = 1, size = 20) Pageable pageable, BaseQuery query) {
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.QUERY_KEY, query);
        IPage<B> pageData = this.service.queryPage(pageable, query, hookContext);
        List<V> data = BeanUtil.copyToList(pageData.getRecords(), this.voClass);
        if (CollectionUtil.isNotEmpty(data)) {
            data.forEach(this::translation);
        }
        return JsonPageResult.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageData.getTotal(),
            data
        );
    }

    /**
     * 创建实体
     *
     * @param createDTO 创建DTO
     * @return 结果
     */
    protected JsonResult<Void> createEntity(C createDTO) {
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.CREATE_DTO_KEY, createDTO);
        this.service.create(BeanUtil.copyProperties(createDTO, this.boClass), hookContext);
        return JsonResult.success();
    }

    /**
     * 批量创建实体
     *
     * @param createDTOList 创建DTO列表
     * @return 结果
     */
    protected JsonResult<Void> createEntity(List<C> createDTOList) {
        if (createDTOList == null || createDTOList.isEmpty()) {
            return JsonResult.fail("批量新增失败！新增实体列表为空！");
        }
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.CREATE_DTO_LIST_KEY, createDTOList);
        this.service.batchCreate(BeanUtil.copyToList(createDTOList, this.boClass), hookContext);
        return JsonResult.success();
    }

    /**
     * 修改实体
     *
     * @param modifyDTO 修改DTO
     * @return 结果
     */
    protected JsonResult<Void> modifyEntity(M modifyDTO) {
        if (modifyDTO.getId() == null) {
            return JsonResult.fail("修改实体失败！实体ID为空！");
        }
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.MODIFY_DTO_KEY, modifyDTO);
        this.service.modify(BeanUtil.copyProperties(modifyDTO, this.boClass), hookContext);
        return JsonResult.success();
    }

    /**
     * 批量修改实体
     *
     * @param modifyDTOList 修改DTO列表
     * @return 结果
     */
    protected JsonResult<Void> modifyEntity(List<M> modifyDTOList) {
        if (modifyDTOList == null || modifyDTOList.isEmpty()) {
            return JsonResult.fail("批量修改失败！修改实体列表为空！");
        }
        Map<Long, M> mapping = modifyDTOList
            .stream()
            .collect(Collectors.toMap(M::getId, Function.identity()));
        HookContext hookContext = HookContext.newContext();
        hookContext.set(HookConstants.MODIFY_DTO_MAP_KEY, mapping);
        this.service.batchModify(BeanUtil.copyToList(modifyDTOList, this.boClass), hookContext);
        return JsonResult.success();
    }

    /**
     * 删除实体
     *
     * @param id ID
     * @return 结果
     */
    protected JsonResult<Void> deleteEntity(Long id) {
        if (id == null) {
            return JsonResult.fail("id不能为空");
        }
        this.service.delete(id);
        return JsonResult.success();
    }

    /**
     * 批量删除实体
     *
     * @param ids ID列表
     * @return 结果
     */
    protected JsonResult<Void> deleteEntity(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return JsonResult.fail("id不能为空");
        }
        this.service.batchDelete(ids);
        return JsonResult.success();
    }

    /**
     * 翻译
     *
     * @param vo VO对象
     */
    private void translation(V vo) {
        if (vo == null) {
            return;
        }
        GenericTranslationSupport.translate(vo);
    }
}
