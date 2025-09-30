package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.common.utils.AuthenticationUtils;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.*;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.domain.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final IUserService userService;

    /**
     * 获取个人信息
     *
     * @return 个人信息
     */
    @GetMapping
    public JsonResult<UserProfileVO> profile() {
        Long userId = AuthenticationUtils.getLoginUserId();
        User user = this.userService.getById(userId);
        if (user == null) {
            return JsonResult.fail("获取用户信息失败！用户不存在！");
        }
        return JsonResult.success(BeanUtil.copyProperties(user, UserProfileVO.class));
    }

    /**
     * 获取站长信息
     *
     * @return 站长信息
     */
    @GetMapping("/master")
    public JsonResult<UserProfileVO> masterInfo() {
        return JsonResult.success(BeanUtil.copyProperties(this.userService.getById(SystemConstants.MASTER_USER_ID), UserProfileVO.class));
    }
}
