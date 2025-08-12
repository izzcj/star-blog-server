package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.CreateUserDTO;
import com.ale.starblog.admin.system.domain.pojo.user.ModifyUserDTO;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.framework.core.service.IBaseService;

/**
 * 用户服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
public interface IUserService extends IBaseService<User, UserBO, CreateUserDTO, ModifyUserDTO> {

    /**
     * 修改用户密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    void changePassword(Long id, String newPassword, String oldPassword);

}
