package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.*;
import com.ale.starblog.admin.system.service.IMenuService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult<MenuVO> fetchDetail(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    @GetMapping("/tree")
    public JsonResult<List<MenuVO>> fetchMenuTree() {
        return JsonResult.success(
            BeanUtil.copyToList(this.service.queryMenuTreeByUserId(SecurityUtils.getLoginUserId()), MenuVO.class)
        );
    }

    /**
     * 获取路由
     *
     * @return 路由树
     */
    @GetMapping("/routers")
    public JsonResult<List<MenuRouterVO>> fetchRouters() {
        List<MenuBO> menuBOList = this.service.queryMenuTreeByUserId(SecurityUtils.getLoginUserId());
        return JsonResult.success(
            BeanUtil.copyToList(menuBOList, MenuRouterVO.class)
        );
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
