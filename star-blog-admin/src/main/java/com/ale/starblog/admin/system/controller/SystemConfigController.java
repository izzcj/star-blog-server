package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.*;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置接口
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController extends BaseController<SystemConfig, ISystemConfigService, SystemConfigVO, SystemConfigBO, CreateSystemConfigDTO, ModifySystemConfigDTO> {

    /**
     * 通过id获取系统配置
     *
     * @param id 系统配置id
     * @return 系统配置VO
     */
    @GetMapping("/{id}")
    public JsonResult<SystemConfigVO> fetchDetails(@PathVariable("id") Long id) {
        return this.queryById(id);
    }

    /**
     * 获取系统配置
     *
     * @param query 查询条件
     * @return 系统配置VO
     */
    @GetMapping
    public JsonResult<SystemConfigVO> fetchOne(SystemConfigQuery query) {
        return this.queryOne(query);
    }

    /**
     * 分页获取系统配置
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 系统配置分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<SystemConfigVO>> fetchPage(Pageable pageable, SystemConfigQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 获取系统配置列表
     *
     * @param query 查询条件
     * @return 系统配置列表
     */
    @GetMapping("/list")
    public JsonResult<List<SystemConfigVO>> fetchList(SystemConfigQuery query) {
        return this.queryList(query);
    }

    /**
     * 创建系统配置
     *
     * @param createDTO 创建系统配置DTO
     * @return 系统配置VO
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody CreateSystemConfigDTO createDTO) {
        return this.createEntity(createDTO);
    }

    /**
     * 修改系统配置
     *
     * @param modifyDTO 修改系统配置DTO
     * @return 系统配置VO
     */
    @PutMapping()
    public JsonResult<Void> modify(@RequestBody ModifySystemConfigDTO modifyDTO) {
        return this.modifyEntity(modifyDTO);
    }

    /**
     * 删除系统配置
     *
     * @param id 系统配置ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> remove(@PathVariable("id") Long id) {
        return this.deleteEntity(id);
    }
}