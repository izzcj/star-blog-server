package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 用户服务接口
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
public interface IUserService extends ICrudService<User, UserBO> {

    /**
     * 修改用户密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    void changePassword(Long id, String newPassword, String oldPassword);

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

}
