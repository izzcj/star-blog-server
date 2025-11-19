package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.*;
import com.ale.starblog.admin.system.service.IDictDataService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:45
 */
@RestController
@RequestMapping("/system/dict-data")
public class DictDataController extends BaseController<DictData, IDictDataService, DictDataVO, DictDataBO, CreateDictDataDTO, ModifyDictDataDTO> {

    /**
     * 通过id获取字典数据
     *
     * @param id 字典数据id
     * @return 字典数据VO
     */
    @GetMapping("/{id}")
    public JsonResult<DictDataVO> fetchDetails(@PathVariable("id") Long id) {
        return this.queryById(id);
    }

    /**
     * 分页获取字典数据
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 字典数据分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<DictDataVO>> fetchPage(Pageable pageable, DictDataQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 获取字典数据列表
     *
     * @param query 查询条件
     * @return 字典数据列表
     */
    @GetMapping("/list")
    public JsonResult<List<DictDataVO>> fetchList(DictDataQuery query) {
        return this.queryList(query);
    }

    /**
     * 获取字典数据选项
     *
     * @param query 查询条件
     * @return 字典数据选项
     */
    @GetMapping("/options")
    public JsonResult<List<Option>> fetchOptions(DictDataQuery query) {
        List<DictDataBO> result = this.service.queryList(query);
        return JsonResult.success(
            result.stream()
                .map(dictDataBO -> Option.of(dictDataBO.getDictLabel(), dictDataBO.getDictValue(), dictDataBO.getDictKey()))
                .collect(Collectors.toList())
        );
    }

    /**
     * 新增字典数据
     *
     * @param createDictDataDTO 创建字典数据dto
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateDictDataDTO createDictDataDTO) {
        return this.createEntity(createDictDataDTO);
    }

    /**
     * 修改字典数据
     *
     * @param modifyDictDataDTO 修改字典数据dto
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyDictDataDTO modifyDictDataDTO) {
        return this.modifyEntity(modifyDictDataDTO);
    }

    /**
     * 删除系统字典数据
     *
     * @param ids 系统字典数据id集合
     * @return Void
     */
    @DeleteMapping("/{ids}")
    public JsonResult<Void> delete(@PathVariable("ids") List<Long> ids) {
        return this.deleteEntity(ids);
    }

}
