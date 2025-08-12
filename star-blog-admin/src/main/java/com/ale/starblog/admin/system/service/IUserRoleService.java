package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.UserRole;
import com.ale.starblog.admin.system.domain.pojo.role.AuthUserRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.admin.system.domain.pojo.user.UserQuery;
import com.ale.starblog.framework.common.porxy.ProxyResolvable;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户角色服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
public interface IUserRoleService extends IService<UserRole>, ProxyResolvable<IUserRoleService> {

    /**
     * 获取用户所属角色集合
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    List<RoleBO> queryRoleByUserId(Long userId);

    /**
     * 分页查询已授权角色的用户
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @return 用户分页数据
     */
    IPage<UserBO> queryAllocatedUserPage(Pageable pageable, UserQuery query);

    /**
     * 分页查询未授权角色的用户
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @return 用户分页数据
     */
    IPage<UserBO> queryUnallocatedUserPage(Pageable pageable, UserQuery query);

    /**
     * 改变授权用户
     *
     * @param authUserRoleDTO 授权用户角色信息
     * @param isCancel        是否是取消授权
     */
    void changeAuthUser(AuthUserRoleDTO authUserRoleDTO, boolean isCancel);

    /**
     * 授权用户
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void authUser(Long userId, List<Long> roleIds);

    /**
     * 判断用户是否是超级管理员
     *
     * @param userId 用户id
     * @return true-是超级管理员 false-不是
     */
    boolean judgeUserIsAdmin(Long userId);
}
