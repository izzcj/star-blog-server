package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.RoleMenu;
import com.ale.starblog.framework.common.porxy.ProxyResolvable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色菜单服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
public interface IRoleMenuService extends IService<RoleMenu>, ProxyResolvable<IRoleMenuService> {

    /**
     * 根据角色ID集合获取菜单ID集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单ID集合
     */
    Set<Long> fetchMenuIdsByRoleIds(Collection<Long> roleIds);

    /**
     * 保存角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void saveRoleMenu(Long roleId, List<Long> menuIds);

}
