package com.ale.starblog.admin.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.system.domain.entity.Role;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.entity.UserRole;
import com.ale.starblog.admin.system.domain.pojo.user.AuthUserRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.admin.system.domain.pojo.user.UserQuery;
import com.ale.starblog.admin.system.enums.RoleType;
import com.ale.starblog.admin.system.mapper.UserRoleMapper;
import com.ale.starblog.framework.core.query.QueryConditionResolver;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Service
@RequiredArgsConstructor
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 获取用户所属角色集合
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    public List<RoleBO> queryRoleByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        List<UserRole> userRoleList = this.lambdaQuery()
            .eq(UserRole::getUserId, userId)
            .list();
        if (CollectionUtil.isEmpty(userRoleList)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoleList.stream()
            .map(UserRole::getRoleId)
            .toList();
        return this.roleService.lambdaQuery()
            .in(Role::getId, roleIds)
            .list()
            .stream()
            .map(role -> BeanUtil.copyProperties(role, RoleBO.class))
            .toList();
    }

    /**
     * 分页查询已授权角色的用户
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @param roleId   角色ID
     * @return 用户分页数据
     */
    public IPage<UserBO> fetchAuthorizedUserPage(Pageable pageable, UserQuery query, Long roleId) {
        List<Long> userIds = this.lambdaQuery()
            .eq(UserRole::getRoleId, roleId)
            .list()
            .stream()
            .map(UserRole::getUserId)
            .toList();
        if (CollectionUtil.isEmpty(userIds)) {
            return new Page<>();
        }
        query.setIds(userIds);
        return this.userService.queryPage(pageable, query);
    }

    /**
     * 分页查询未授权角色的用户
     *
     * @param pageable 分页参数
     * @param query    查询参数
     * @param roleId   角色ID
     * @return 用户分页数据
     */
    public IPage<UserBO> fetchUnauthorizedUserPage(Pageable pageable, UserQuery query, Long roleId) {
        List<Long> userIds = this.lambdaQuery()
            .eq(UserRole::getRoleId, roleId)
            .list()
            .stream()
            .map(UserRole::getUserId)
            .toList();
        if (CollectionUtil.isEmpty(userIds)) {
            return this.userService.queryPage(pageable, query);
        }
        LambdaQueryWrapper<User> queryWrapper = QueryConditionResolver.resolveLambda(query);
        queryWrapper.notIn(User::getId, userIds);
        return this.userService.executeQueryPage(pageable, queryWrapper, UserBO.class);
    }

    /**
     * 改变授权用户
     *
     * @param authUserRoleDTO 授权用户角色信息
     * @param isCancel        是否是取消授权
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeAuthUser(AuthUserRoleDTO authUserRoleDTO, boolean isCancel) {
        // 取消授权
        if (isCancel) {
            List<UserRole> userRoleList = this.lambdaQuery()
                .eq(UserRole::getRoleId, authUserRoleDTO.getRoleId())
                .in(UserRole::getUserId, authUserRoleDTO.getUserIds())
                .list();
            if (CollectionUtil.isEmpty(userRoleList)) {
                return;
            }
            this.removeBatchByIds(
                userRoleList.stream()
                    .map(UserRole::getId)
                    .toList()
            );
            return;
        }
        Long roleId = authUserRoleDTO.getRoleId();
        List<Long> userIds = authUserRoleDTO.getUserIds();
        List<UserRole> newUserRole = userIds.stream()
            .map(
                userId -> UserRole.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .build()
            )
            .collect(Collectors.toList());
        this.saveBatch(newUserRole);
    }

    /**
     * 授权用户
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void authUser(Long userId, List<Long> roleIds) {
        if (userId == null || CollectionUtil.isEmpty(roleIds)) {
            return;
        }
        // 删除旧角色
        this.remove(
            new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
        );
        List<UserRole> newUserRole = roleIds.stream()
            .map(roleId ->
                UserRole.builder()
                    .userId(userId)
                    .roleId(roleId)
                    .build()
            )
            .collect(Collectors.toList());
        this.saveBatch(newUserRole);
    }

    /**
     * 判断用户是否是超级管理员
     *
     * @param userId 用户id
     * @return true-是超级管理员 false-不是
     */
    public boolean judgeUserIsAdmin(Long userId) {
        if (userId == null) {
            return false;
        }
        List<Long> adminRoleIds = this.roleService.lambdaQuery()
            .select(Role::getId)
            .eq(Role::getType, RoleType.ADMIN.getValue())
            .list()
            .stream()
            .map(Role::getId)
            .toList();
        if (CollectionUtil.isEmpty(adminRoleIds)) {
            return false;
        }
        return this.lambdaQuery()
            .eq(UserRole::getUserId, userId)
            .in(UserRole::getRoleId, adminRoleIds)
            .exists();
    }
}
