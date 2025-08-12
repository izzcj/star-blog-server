package com.ale.starblog.admin.common.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.common.login.AuthUser;
import com.ale.starblog.admin.common.login.UserLoginInfoVO;
import com.ale.starblog.admin.common.utils.AuthenticationUtils;
import com.ale.starblog.admin.system.domain.pojo.role.RoleBO;
import com.ale.starblog.admin.system.service.IUserRoleService;
import com.ale.starblog.framework.common.domain.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权Controller
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/aas")
public class AuthController {

    /**
     * 用户角色服务
     */
    private final IUserRoleService userRoleService;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public JsonResult<UserLoginInfoVO> getInfo() {
        AuthUser authUser = AuthenticationUtils.getAuthenticatedUser();
        List<RoleBO> roleList = this.userRoleService.queryRoleByUserId(authUser.getId());
        authUser.setRoleIds(
            roleList.stream()
                .map(RoleBO::getId)
                .collect(Collectors.toSet())
        );
        return JsonResult.success(BeanUtil.copyProperties(authUser, UserLoginInfoVO.class));
    }


}
