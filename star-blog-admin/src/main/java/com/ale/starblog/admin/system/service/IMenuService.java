package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.CreateMenuDTO;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuBO;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuRouterVO;
import com.ale.starblog.admin.system.domain.pojo.menu.ModifyMenuDTO;
import com.ale.starblog.framework.core.service.IBaseService;

import java.util.Collection;
import java.util.List;

/**
 * 菜单服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
public interface IMenuService extends IBaseService<Menu, MenuBO, CreateMenuDTO, ModifyMenuDTO> {

    /**
     * 通过角色id获取菜单id集合
     *
     * @param roleId 角色id
     * @return 菜单id集合
     */
    List<Long> queryMenuIdsByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuBO> queryMenuTreeByUserId(Long userId);

    /**
     * 构建菜单路由
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<MenuRouterVO> buildMenuRouter(Collection<MenuBO> menus);
}
