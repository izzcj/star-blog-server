package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.Menu;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuBO;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuQuery;
import com.ale.starblog.framework.core.service.ICrudService;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
public interface IMenuService extends ICrudService<Menu, MenuBO, MenuQuery> {

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuBO> queryMenuTreeByUserId(Long userId);
}
