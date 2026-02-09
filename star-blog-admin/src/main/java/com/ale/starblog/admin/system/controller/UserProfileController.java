package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.common.utils.AuthenticationUtils;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.user.*;
import com.ale.starblog.admin.system.service.UserRoleService;
import com.ale.starblog.admin.system.service.UserService;
import com.ale.starblog.framework.common.domain.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 个人信息
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user/profile")
public class UserProfileController {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 用户角色服务
     */
    private final UserRoleService userRoleService;

    /**
     * 获取个人信息
     *
     * @return 个人信息
     */
    @GetMapping
    public JsonResult<UserProfileVO> fetchProfile() {
        Long userId = AuthenticationUtils.getLoginUserId();
        User user = this.userService.getById(userId);
        if (user == null) {
            return JsonResult.fail("获取用户信息失败！用户不存在！");
        }
        UserProfileVO result = BeanUtil.copyProperties(user, UserProfileVO.class);
        result.setAdmin(this.userRoleService.judgeUserIsAdmin(userId));
        List<RoleBO> roleList = this.userRoleService.queryRoleByUserId(userId);
        result.setRoleIds(
            roleList.stream()
                .map(RoleBO::getId)
                .collect(Collectors.toSet())
        );
        return JsonResult.success(result);
    }

    /**
     * 修改个人信息
     *
     * @param modifyUserDTO 修改个人信息DTO
     * @return 修改个人信息结果
     */
    @PutMapping
    public JsonResult<Void> modifyProfile(@RequestBody ModifyUserDTO modifyUserDTO) {
        modifyUserDTO.setId(AuthenticationUtils.getLoginUserId());
        this.userService.modify(BeanUtil.copyProperties(modifyUserDTO, UserBO.class));
        return JsonResult.success();
    }

    /**
     * 修改密码
     *
     * @param changePasswordDTO 修改密码DTO
     * @return 修改密码结果
     */
    @PutMapping("/password")
    public JsonResult<Void> changePassword(@RequestBody ChangeUserPasswordDTO changePasswordDTO) {
        this.userService.changePassword(AuthenticationUtils.getLoginUserId(), changePasswordDTO.getNewPassword(), changePasswordDTO.getOldPassword());
        return JsonResult.success();
    }

    /**
     * 获取站长信息
     *
     * @return 站长信息
     */
    @GetMapping("/master")
    public JsonResult<UserProfileVO> fetchMasterInfo() {
        return JsonResult.success(BeanUtil.copyProperties(this.userService.getById(SystemConstants.MASTER_USER_ID), UserProfileVO.class));
    }
}
