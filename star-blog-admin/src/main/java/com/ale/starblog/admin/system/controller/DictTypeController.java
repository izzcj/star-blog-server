package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.*;
import com.ale.starblog.admin.system.service.IDictTypeService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:13
 */
@RestController
@RequestMapping("/system/dict/type")
public class DictTypeController extends BaseController<DictType, IDictTypeService, DictTypeVO, DictTypeBO, CreateDictTypeDTO, ModifyDictTypeDTO> {

    /**
     * 通过id获取字典类型
     *
     * @param id 字典类型id
     * @return 字典类型VO
     */
    @GetMapping("/{id}")
    public JsonResult<DictTypeVO> get(@PathVariable("id") Long id) {
        return this.queryById(id);
    }

    /**
     * 分页获取字典类型
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 字典类型分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<DictTypeVO>> page(Pageable pageable, DictTypeQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 获取字典类型列表
     *
     * @param query 查询条件
     * @return 字典类型列表
     */
    @GetMapping("/list")
    public JsonResult<List<DictTypeVO>> list(DictTypeQuery query) {
        return this.queryList(query);
    }

    /**
     * 新增字典类型
     *
     * @param createDictTypeDTO 创建字典类型dto
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> add(@RequestBody @Validated CreateDictTypeDTO createDictTypeDTO) {
        return this.createEntity(createDictTypeDTO);
    }

    /**
     * 修改字典类型
     *
     * @param modifyDictTypeDTO 修改字典类型dto
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> edit(@RequestBody @Validated ModifyDictTypeDTO modifyDictTypeDTO) {
        return this.modifyEntity(modifyDictTypeDTO);
    }

    /**
     * 删除系统字典类型
     *
     * @param ids 系统字典类型id集合
     * @return Void
     */
    @DeleteMapping("/{ids}")
    public JsonResult<Void> delete(@PathVariable("ids") List<Long> ids) {
        return this.deleteEntity(ids);
    }

    /**
     * 刷新字典缓存
     *
     * @return Void
     */
    @PostMapping("/refresh/cache")
    public JsonResult<Void> refreshCache() {
        this.service.refreshCache();
        return JsonResult.success();
    }

}
