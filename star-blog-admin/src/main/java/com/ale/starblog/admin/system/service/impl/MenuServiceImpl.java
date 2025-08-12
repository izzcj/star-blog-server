package com.ale.starblog.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.entity.RoleMenu;
import com.ale.starblog.admin.system.domain.pojo.menu.*;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.enums.MenuType;
import com.ale.starblog.admin.system.mapper.MenuMapper;
import com.ale.starblog.admin.system.service.IMenuService;
import com.ale.starblog.admin.system.service.IRoleMenuService;
import com.ale.starblog.admin.system.service.IUserRoleService;
import com.ale.starblog.admin.system.support.MenuRouterHelper;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.common.utils.TreeUtils;
import com.ale.starblog.framework.core.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu, MenuBO, CreateMenuDTO, ModifyMenuDTO> implements IMenuService {

    /**
     * 用户角色服务
     */
    private final IUserRoleService userRoleService;

    /**
     * 角色菜单服务
     */
    private final IRoleMenuService roleMenuService;

    @Override
    public List<Long> queryMenuIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }
        if (this.userRoleService.judgeUserIsAdmin(roleId)) {
            List<Menu> menuList = this.lambdaQuery()
                .select(Menu::getId)
                .eq(Menu::getStatus, SwitchStatus.ENABLE)
                .list();
            return menuList.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        }
        return this.roleMenuService.lambdaQuery()
            .eq(RoleMenu::getRoleId, roleId)
            .list()
            .stream()
            .map(RoleMenu::getMenuId)
            .toList();
    }

    @Override
    public List<MenuBO> queryMenuTreeByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        List<Menu> menuList;
        // 超级管理员能查看所有菜单
        boolean isAdmin = this.userRoleService.judgeUserIsAdmin(userId);
        if (isAdmin) {
            menuList = this.lambdaQuery()
                .in(Menu::getMenuType, Arrays.asList(MenuType.CATALOGUE.getValue(), MenuType.MENU.getValue()))
                .eq(Menu::getStatus, SwitchStatus.ENABLE.getValue())
                .list();
        } else {
            // 获取用户所属角色
            List<Long> menuIds = this.queryMenuIdsByUserId(userId);
            if (CollectionUtils.isEmpty(menuIds)) {
                return Collections.emptyList();
            }
            menuList = this.lambdaQuery()
                .in(Menu::getId, menuIds)
                .list();
        }
        List<MenuBO> menuBOList = BeanUtil.copyToList(menuList, MenuBO.class);
        return TreeUtils.buildTree(menuBOList);
    }

    @Override
    public List<MenuRouterVO> buildMenuRouter(Collection<MenuBO> menus) {
        List<MenuRouterVO> routers = Lists.newArrayListWithCapacity(menus.size());
        for (MenuBO menu : menus) {
            MenuRouterVO router = MenuRouterVO.builder()
                .hidden(!menu.getVisible())
                .name(MenuRouterHelper.getRouteName(menu))
                .path(MenuRouterHelper.getRouterPath(menu))
                .component(MenuRouterHelper.getComponent(menu))
                .meta(MenuRouterHelper.buildMeta(menu))
                .query(menu.getQuery())
                .build();
            Collection<MenuBO> menuChildren = menu.getChildren();
            if (CollectionUtil.isNotEmpty(menuChildren) && Objects.equals(menu.getMenuType(), MenuType.CATALOGUE)) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenuRouter(menuChildren));
            } else if (MenuRouterHelper.isMenuFrame(menu)) {
                router.setMeta(null);
                Collection<MenuRouterVO> childrenList = new ArrayList<>();
                MenuRouterVO children = MenuRouterVO
                    .builder()
                    .path(menu.getPath())
                    .component(menu.getComponent())
                    .name(StrUtil.upperFirst(menu.getPath()))
                    .meta(MenuRouterHelper.buildMeta(menu))
                    .query(menu.getQuery())
                    .build();
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (Objects.equals(menu.getParentId(), 0L) && MenuRouterHelper.isInnerLink(menu)) {
                router.setMeta(
                    MenuRouterMeta
                        .builder()
                        .title(menu.getMenuName())
                        .icon(menu.getIcon())
                        .build()
                );
                router.setPath("/");
                Collection<MenuRouterVO> childrenList = new ArrayList<>();
                String routerPath = MenuRouterHelper.innerLinkReplaceEach(menu.getPath());
                MenuRouterVO children = MenuRouterVO
                    .builder()
                    .path(routerPath)
                    .component(MenuRouterHelper.INNER_LINK)
                    .name(StrUtil.upperFirst(routerPath))
                    .meta(
                        MenuRouterMeta
                            .builder()
                            .title(menu.getMenuName())
                            .icon(menu.getIcon())
                            .link(menu.getPath())
                            .build()
                    )
                    .build();
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取用户可用菜单ID集合
     *
     * @param userId 用户id
     * @return 系统菜单id集合
     */
    private List<Long> queryMenuIdsByUserId(Long userId) {
        List<RoleBO> userRoles = this.userRoleService.queryRoleByUserId(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoles.stream()
            .map(RoleBO::getId)
            .collect(Collectors.toList());
        List<RoleMenu> roleMenus = this.roleMenuService
            .lambdaQuery()
            .in(RoleMenu::getRoleId, roleIds)
            .list();
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }
        return roleMenus.stream()
            .map(RoleMenu::getMenuId)
            .collect(Collectors.toList());
    }

}
