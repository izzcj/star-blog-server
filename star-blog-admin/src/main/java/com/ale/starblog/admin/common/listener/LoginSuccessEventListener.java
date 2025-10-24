package com.ale.starblog.admin.common.listener;

import com.ale.starblog.admin.common.login.AuthUser;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.common.utils.ServletUtils;
import com.ale.starblog.framework.security.event.VenusLoginSuccessEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 登录成功事件监听器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessEventListener implements ApplicationListener<VenusLoginSuccessEvent> {

    /**
     * 用户服务
     */
    private final IUserService userService;

    @Override
    public void onApplicationEvent(@NonNull VenusLoginSuccessEvent event) {
        AuthUser authUser = CastUtils.cast(event.getAuthenticatedUser());
        if (authUser == null) {
            return;
        }
        HttpServletRequest request = ServletUtils.getRequest();
        this.userService.lambdaUpdate()
            .eq(User::getId, authUser.getId())
            .set(User::getLastLoginIp, request.getRemoteAddr())
            .set(User::getLastLoginTime, LocalDateTime.now())
            .update();
    }

}
