package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.role.AuthUserRoleDTO;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.user.*;
import com.ale.starblog.admin.system.service.IUserRoleService;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.domain.JsonPageResult;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.controller.BaseController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * /系统管理/用户管理
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserController extends BaseController<User, IUserService, UserVO, UserBO, CreateUserDTO, ModifyUserDTO> {

    /**
     * 用户角色服务
     */
    private final IUserRoleService userRoleService;

    /**
     * 通过id获取用户
     *
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public JsonResult<UserVO> fetchDetails(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 获取用户所属角色id集合
     *
     * @param id 用户id
     * @return 结果
     */
    @GetMapping("/role/{id}")
    public JsonResult<List<Long>> fetchRoleIdsByUserId(@PathVariable(name = "id") Long id) {
        if (id == null) {
            return JsonResult.fail("id不能为空");
        }
        List<RoleBO> roleList = this.userRoleService.queryRoleByUserId(id);
        List<Long> roleIds = roleList.stream()
            .map(RoleBO::getId)
            .toList();
        return JsonResult.success(roleIds);
    }

    /**
     * 分页获取已分配角色用户
     *
     * @param pageable 分页信息
     * @param query    查询条件
     * @return 用户分页数据
     */
    @GetMapping("/allocated")
    public JsonResult<JsonPageResult.PageData<UserVO>> fetchAllocatedUser(Pageable pageable, UserQuery query) {
        if (query.getRoleId() == null) {
            return JsonResult.fail("角色ID不能为空");
        }
        IPage<UserBO> allocatedUserPage = this.userRoleService.queryAllocatedUserPage(pageable, query);
        return JsonPageResult.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            allocatedUserPage.getTotal(),
            BeanUtil.copyToList(allocatedUserPage.getRecords(), UserVO.class)
        );
    }

    /**
     * 分页获取未分配角色的用户
     *
     * @param pageable 分页信息
     * @param query    查询条件
     * @return 用户分页数据
     */
    @GetMapping("/unallocated")
    public JsonResult<JsonPageResult.PageData<UserVO>> fetchUnallocatedUser(Pageable pageable, UserQuery query) {
        if (query.getRoleId() == null) {
            return JsonResult.fail("角色ID不能为空");
        }
        IPage<UserBO> unallocatedUserPage = this.userRoleService.queryUnallocatedUserPage(pageable, query);
        return JsonPageResult.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            unallocatedUserPage.getTotal(),
            BeanUtil.copyToList(unallocatedUserPage.getRecords(), UserVO.class)
        );
    }

    /**
     * 取消用户授权
     *
     * @param authUserRoleDTO 授权用户角色信息
     * @return Void
     */
    @PostMapping("/auth/cancel")
    public JsonResult<Void> authUserCancel(@RequestBody @Validated AuthUserRoleDTO authUserRoleDTO) {
        this.userRoleService.changeAuthUser(authUserRoleDTO, true);
        return JsonResult.success();
    }

    /**
     * 授权用户
     *
     * @param authUserRoleDTO 授权用户角色信息
     * @return Void
     */
    @PostMapping("/auth")
    public JsonResult<Void> authUser(@RequestBody @Validated AuthUserRoleDTO authUserRoleDTO) {
        this.userRoleService.changeAuthUser(authUserRoleDTO, false);
        return JsonResult.success();
    }

    /**
     * 分页获取用户
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 用户分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<UserVO>> fetchPage(Pageable pageable, UserQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 创建用户
     *
     * @param createUserDTO 创建用户dto
     *
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return this.createEntity(createUserDTO);
    }

    /**
     * 修改用户
     *
     * @param modifyUserDTO 修改用户dto
     *
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyUserDTO modifyUserDTO) {
        List<Long> roleIds = modifyUserDTO.getRoleIds();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            this.userRoleService.authUser(modifyUserDTO.getId(), roleIds);
        }
        return this.modifyEntity(modifyUserDTO);
    }

    /**
     * 重置密码
     *
     * @param changeUserPasswordDTO 修改用户密码dto
     * @return Void
     */
    @PutMapping("/password")
    public JsonResult<Void> restPassword(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO) {
        this.service.changePassword(SecurityUtils.getLoginUserId(), changeUserPasswordDTO.getNewPassword(), changeUserPasswordDTO.getOldPassword());
        return JsonResult.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return Void
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }
}
