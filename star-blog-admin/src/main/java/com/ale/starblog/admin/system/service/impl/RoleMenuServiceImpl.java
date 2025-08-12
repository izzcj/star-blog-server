package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.system.domain.entity.RoleMenu;
import com.ale.starblog.admin.system.mapper.RoleMenuMapper;
import com.ale.starblog.admin.system.service.IRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色菜单服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        // 删除旧角色菜单
        this.remove(
            new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId)
        );
        List<RoleMenu> newRoleMenu = menuIds.stream()
            .map(menuId ->
                RoleMenu.builder()
                    .roleId(roleId)
                    .menuId(menuId)
                    .build()
            )
            .collect(Collectors.toList());
        this.resolveProxy().saveBatch(newRoleMenu);
    }
}
