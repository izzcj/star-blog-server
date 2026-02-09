package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.pojo.role.*;
import com.ale.starblog.admin.system.service.RoleMenuService;
import com.ale.starblog.admin.system.service.RoleService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.core.controller.BaseController;
import com.ale.starblog.framework.core.convert.OptionConvertible;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
public class RoleController extends BaseController<Role, RoleService, RoleVO, RoleBO, RoleQuery, CreateRoleDTO, ModifyRoleDTO> {

    /**
     * 角色菜单服务
     */
    private final RoleMenuService roleMenuService;

    /**
     * 获取角色选项
     *
     * @param query 查询条件
     * @return 角色选项
     */
    @GetMapping("/options")
    public JsonResult<List<Option>> fetchOptions(RoleQuery query) {
        List<RoleBO> result = this.service.queryList(query);
        return JsonResult.success(
            result.stream()
                .map(OptionConvertible::convert)
                .toList()
        );
    }

    /**
     * 获取角色菜单权限
     *
     * @param id 角色id
     * @return 菜单id
     */
    @GetMapping("/{id}/menu")
    public JsonResult<Set<Long>> fetchRoleMenuPermissions(@PathVariable Long id) {
        return JsonResult.success(
            this.roleMenuService.fetchMenuIdsByRoleIds(Collections.singleton(id))
        );
    }

    /**
     * 保存角色菜单权限
     *
     * @param id      角色id
     * @param menuIds 菜单id
     * @return 结果
     */
    @PostMapping("/{id}/menu")
    public JsonResult<Void> saveRoleMenuPermissions(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        this.roleMenuService.saveRoleMenu(id, menuIds);
        return JsonResult.success();
    }

}
