package com.ale.starblog.admin.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.*;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.mapper.MenuMapper;
import com.ale.starblog.framework.common.utils.TreeUtils;
import com.ale.starblog.framework.core.service.AbstractCrudService;
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
public class MenuService extends AbstractCrudService<MenuMapper, Menu, MenuBO, MenuQuery> {

    /**
     * 用户角色服务
     */
    private final UserRoleService userRoleService;

    /**
     * 角色菜单服务
     */
    private final RoleMenuService roleMenuService;

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<MenuBO> queryMenuTreeByUserId(Long userId) {
        List<Menu> menuList;
        // 未登录只获取公共菜单
        if (userId == null) {
            menuList = this.lambdaQuery()
                .eq(Menu::getEnabled, true)
                .eq(Menu::getCommon, true)
                .list();
        } else {
            // 超级管理员能查看所有菜单
            boolean isAdmin = this.userRoleService.judgeUserIsAdmin(userId);
            if (isAdmin) {
                menuList = this.lambdaQuery()
                    .list();
            } else {
                Set<Long> menuIds = this.queryMenuIdsByUserId(userId);
                menuList = this.lambdaQuery()
                    .eq(Menu::getEnabled, true)
                    .and(
                        q -> q.in(CollectionUtil.isNotEmpty(menuIds), Menu::getId, menuIds)
                            .or()
                            .eq(Menu::getCommon, true)
                    )
                    .list();
            }
        }
        List<MenuBO> menuBOList = BeanUtil.copyToList(menuList, MenuBO.class);
        return TreeUtils.buildTree(menuBOList);
    }

    /**
     * 获取用户可用菜单ID集合
     *
     * @param userId 用户id
     * @return 系统菜单id集合
     */
    private Set<Long> queryMenuIdsByUserId(Long userId) {
        List<RoleBO> userRoles = this.userRoleService.queryRoleByUserId(userId);
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptySet();
        }
        List<Long> roleIds = userRoles.stream()
            .map(RoleBO::getId)
            .collect(Collectors.toList());
        return this.roleMenuService.fetchMenuIdsByRoleIds(roleIds);
    }

}
