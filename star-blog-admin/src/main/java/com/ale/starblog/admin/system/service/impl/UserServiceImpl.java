package com.ale.starblog.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.admin.system.mapper.UserMapper;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractCrudServiceImpl<UserMapper, User, UserBO> implements IUserService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public void beforeCreate(User entity, HookContext context) {
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword(this.passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        } else {
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
        }
    }

    @Override
    public void changePassword(Long id, String newPassword, String oldPassword) {
        User user = this.getOptById(id)
            .orElseThrow(() -> new ServiceException("修改密码失败！用户[{}]不存在！", id));
        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("修改密码失败！原密码错误！");
        }
        this.lambdaUpdate()
            .eq(User::getId, id)
            .set(User::getPassword, this.passwordEncoder.encode(newPassword))
            .update();
    }

    @Override
    public void resetPassword(Long id) {
        User user = this.getOptById(id)
            .orElseThrow(() -> new ServiceException("重置密码失败！用户[{}]不存在！", id));
        user.setPassword(this.passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        this.updateById(user);
    }
}
