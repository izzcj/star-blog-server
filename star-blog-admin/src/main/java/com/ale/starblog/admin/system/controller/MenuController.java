package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.*;
import com.ale.starblog.admin.system.service.MenuService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
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
public class MenuController extends BaseController<Menu, MenuService, MenuVO, MenuBO, MenuQuery, CreateMenuDTO, ModifyMenuDTO> {

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
}
