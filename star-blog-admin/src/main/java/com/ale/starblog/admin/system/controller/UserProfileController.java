package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.common.utils.AuthenticationUtils;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.domain.pojo.user.*;
import com.ale.starblog.admin.system.service.IUserRoleService;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class UserProfileController extends BaseController<User, IUserService, UserVO, UserBO, CreateUserDTO, ModifyUserDTO> {

    /**
     * 用户角色服务
     */
    private final IUserRoleService userRoleService;

    /**
     * 获取个人信息
     *
     * @return 个人信息
     */
    @GetMapping
    public JsonResult<UserProfileVO> profile() {
        Long userId = AuthenticationUtils.getLoginUserId();
        User user = this.service.getById(userId);
        if (user == null) {
            return JsonResult.fail("获取用户信息失败！用户不存在！");
        }
        List<RoleBO> roleList = this.userRoleService.queryRoleByUserId(userId);
        String userRoleNames = roleList.stream()
            .map(RoleBO::getRoleName)
            .collect(Collectors.joining(","));
        return JsonResult.success(UserProfileVO.builder()
                .user(BeanUtil.copyProperties(user, UserVO.class))
                .roleGroup(userRoleNames)
                .build());
    }
}
