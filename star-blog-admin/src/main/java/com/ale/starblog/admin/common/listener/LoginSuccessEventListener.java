package com.ale.starblog.admin.common.listener;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.admin.common.login.AuthUser;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.listener.DailyStatInfoChangeEvent;
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
        User user = this.userService.getById(authUser.getId());
        if (user == null) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        // 上次登录时间不是今天则更新活跃用户数
        if (!LocalDateTimeUtil.isSameDay(user.getLastLoginTime(), now)) {
            SpringUtil.publishEvent(new DailyStatInfoChangeEvent(this, StatInfoConstants.STAT_INFO_TYPE_ACTIVE_COUNT, 1));
        }
        this.userService.lambdaUpdate()
            .eq(User::getId, authUser.getId())
            .set(User::getLastLoginIp, request.getRemoteAddr())
            .set(User::getLastLoginTime, now)
            .update();
    }

}
