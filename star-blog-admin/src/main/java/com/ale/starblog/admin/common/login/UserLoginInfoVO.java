package com.ale.starblog.admin.common.login;


import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * 用户登录信息VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/24
 */
@Data
@Builder
public class UserLoginInfoVO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否为管理员
     */
    private Boolean admin;

    /**
     * 用户角色ID集合
     */
    private Set<String> roleIds;

    /**
     * 权限列表
     */
    private Set<String> permissions;
}
