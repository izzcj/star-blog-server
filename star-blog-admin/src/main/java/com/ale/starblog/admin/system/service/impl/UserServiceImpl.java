package com.ale.starblog.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.CreateUserDTO;
import com.ale.starblog.admin.system.domain.pojo.user.ModifyUserDTO;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.admin.system.mapper.UserMapper;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.core.service.BaseServiceImpl;
import com.ale.starblog.framework.core.service.HookContext;
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
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserBO, CreateUserDTO, ModifyUserDTO> implements IUserService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public void beforeCreate(User entity, HookContext context) {
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword(this.passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        }
    }

    @Override
    public void changePassword(Long id, String newPassword, String oldPassword) {
        User user = this.getById(id);
        if (user == null) {
            throw new ServiceException("修改密码失败！用户不存在！");
        }
        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("修改密码失败！原密码错误！");
        }
        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.updateById(user);
    }
}
