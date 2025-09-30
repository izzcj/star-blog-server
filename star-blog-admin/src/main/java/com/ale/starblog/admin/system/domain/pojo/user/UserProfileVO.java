package com.ale.starblog.admin.system.domain.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 用户个人信息VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVO {

    /**
     * id
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
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

}
