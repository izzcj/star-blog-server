package com.ale.starblog.admin.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.system.domain.entity.RoleMenu;
import com.ale.starblog.admin.system.mapper.RoleMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色菜单服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    /**
     * 根据角色ID集合获取菜单ID集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单ID集合
     */
    public Set<Long> fetchMenuIdsByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        List<RoleMenu> roleMenus = this.lambdaQuery()
            .in(RoleMenu::getRoleId, roleIds)
            .list();
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptySet();
        }
        return roleMenus.stream()
            .map(RoleMenu::getMenuId)
            .collect(Collectors.toSet());
    }

    /**
     * 保存角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        // 删除旧角色菜单
        this.remove(
            new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId)
        );
        if (CollectionUtil.isEmpty(menuIds)) {
            return;
        }
        List<RoleMenu> newRoleMenu = menuIds.stream()
            .map(menuId ->
                RoleMenu.builder()
                    .roleId(roleId)
                    .menuId(menuId)
                    .build()
            )
            .collect(Collectors.toList());
        this.saveBatch(newRoleMenu);
    }
}
