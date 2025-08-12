package com.ale.starblog.admin.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.pojo.role.*;
import com.ale.starblog.admin.system.service.IRoleMenuService;
import com.ale.starblog.admin.system.service.IRoleService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理/角色管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
public class RoleController extends BaseController<Role, IRoleService, RoleVO, RoleBO, CreateRoleDTO, ModifyRoleDTO> {

    /**
     * 角色菜单服务
     */
    private final IRoleMenuService roleMenuService;

    /**
     * 根据id获取角色
     *
     * @param id 角色id
     * @return 结果
     */
    @GetMapping("/{id}")
    public JsonResult<RoleVO> get(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 分页获取角色
     *
     * @param pageable 分页信息
     * @param query    查询条件
     * @return 结果
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<RoleVO>> page(Pageable pageable, RoleQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 获取角色列表
     *
     * @param query 查询条件
     * @return 结果
     */
    @GetMapping("/list")
    public JsonResult<List<RoleVO>> list(RoleQuery query) {
        return this.queryList(query);
    }

    /**
     * 新增角色
     *
     * @param createRoleDTO 创建角色dto
     * @return 结果
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateRoleDTO createRoleDTO) {
        if (CollectionUtil.isNotEmpty(createRoleDTO.getMenuIds())) {
            this.roleMenuService.saveRoleMenu(createRoleDTO.getId(), createRoleDTO.getMenuIds());
        }
        return this.createEntity(createRoleDTO);
    }

    /**
     * 修改系统角色
     *
     * @param modifyRoleDTO 修改角色dto
     * @return 结果
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyRoleDTO modifyRoleDTO) {
        if (CollectionUtil.isNotEmpty(modifyRoleDTO.getMenuIds())) {
            this.roleMenuService.saveRoleMenu(modifyRoleDTO.getId(), modifyRoleDTO.getMenuIds());
        }
        return this.modifyEntity(modifyRoleDTO);
    }

    /**
     * 删除系统角色
     *
     * @param id 角色id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }
}
