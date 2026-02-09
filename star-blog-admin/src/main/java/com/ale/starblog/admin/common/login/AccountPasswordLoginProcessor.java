package com.ale.starblog.admin.common.login;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.service.UserRoleService;
import com.ale.starblog.admin.system.service.UserService;
import com.ale.starblog.framework.common.security.AuthenticatedUser;
import com.ale.starblog.framework.security.authentication.LoginProcessor;
import com.ale.starblog.framework.security.enums.LoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 账号密码登录处理器
 * 默认实现
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/26
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountPasswordLoginProcessor implements LoginProcessor {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 用户角色服务
     */
    private final UserRoleService userRoleService;

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticatedUser login(Map<String, Object> parameters) {
        String account = MapUtil.getStr(parameters, "account");
        String password = MapUtil.getStr(parameters, "password");

        if (StrUtil.hasBlank(account, password)) {
            throw new AuthenticationServiceException("参数[账号、密码]不能为空");
        }

        AuthUser authenticatedUser = this.loadByAccount(account);
        if (authenticatedUser == null) {
            throw new AuthenticationServiceException("账号或密码错误");
        }

        if (!this.passwordEncoder.matches(password, authenticatedUser.getPassword())) {
            throw new BadCredentialsException("账号或密码错误");
        }

        // 加载用户信息
        this.loadUserInfo(authenticatedUser);

        return authenticatedUser;
    }

    /**
     * 根据账号加载用户
     *
     * @param account 账号
     * @return 用户
     */
    private AuthUser loadByAccount(String account) {
        User user = this.userService.lambdaQuery()
            .eq(User::getAccount, account)
            .one();
        if (user == null) {
            log.warn("用户不存在：{}", account);
            return null;
        }
        return AuthUser.builder()
            .id(user.getId())
            .account(user.getAccount())
            .password(user.getPassword())
            .username(user.getNickname())
            .avatar(user.getAvatar())
            .build();
    }

    /**
     * 加载用户信息
     *
     * @param authenticatedUser 已认证的用户
     */
    private void loadUserInfo(AuthUser authenticatedUser) {
        authenticatedUser.setAdmin(this.userRoleService.judgeUserIsAdmin(authenticatedUser.getId()));
    }

    @Override
    public boolean supports(LoginType loginType) {
        return LoginType.ACCOUNT_PASSWORD.match(loginType);
    }
}
