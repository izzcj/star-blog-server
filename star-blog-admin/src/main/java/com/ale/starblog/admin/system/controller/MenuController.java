package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.*;
import com.ale.starblog.admin.system.service.IMenuService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 系统管理/菜单管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController<Menu, IMenuService, MenuVO, MenuBO, CreateMenuDTO, ModifyMenuDTO> {

    /**
     * 根据id获取菜单
     *
     * @param id 角色id
     * @return 结果
     */
    @GetMapping("/{id}")
    public JsonResult<MenuVO> get(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 获取菜单列表
     *
     * @param query 查询条件
     * @return 结果
     */
    @GetMapping("/list")
    public JsonResult<List<MenuVO>> list(MenuQuery query) {
        return this.queryList(query);
    }

    /**
     * 获取菜单下拉树列表
     *
     * @return 菜单树
     */
    @GetMapping("/tree-select")
    public JsonResult<List<MenuBO>> treeSelect() {
        return JsonResult.success(this.service.queryMenuTreeByUserId(SecurityUtils.getLoginUserId()));
    }

    /**
     * 获取角色对应的菜单树
     *
     * @param roleId 角色id
     * @return 菜单选择树
     */
    @GetMapping("/role/tree-select/{roleId}")
    public JsonResult<?> getRoleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("checkedKeys", this.service.queryMenuIdsByRoleId(roleId));
        result.put("menus", this.service.queryMenuTreeByUserId(SecurityUtils.getLoginUserId()));
        return JsonResult.success(result);
    }

    /**
     * 获取路由
     *
     * @return 路由树
     */
    @GetMapping("/routers")
    public JsonResult<List<MenuRouterVO>> getRouters() {
        List<MenuBO> menuBOList = this.service.queryMenuTreeByUserId(SecurityUtils.getLoginUserId());
        return JsonResult.success(this.service.buildMenuRouter(menuBOList));
    }

    /**
     * 新增菜单
     *
     * @param createMenuDTO 创建菜单dto
     * @return 结果
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateMenuDTO createMenuDTO) {
        return this.createEntity(createMenuDTO);
    }

    /**
     * 修改菜单
     *
     * @param modifyMenuDTO 修改菜单dto
     * @return 结果
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyMenuDTO modifyMenuDTO) {
        return this.modifyEntity(modifyMenuDTO);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }
}
