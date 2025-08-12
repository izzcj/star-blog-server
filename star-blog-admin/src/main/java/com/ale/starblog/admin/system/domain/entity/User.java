package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
 * 系统用户
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseAuditEntity {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

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
    private Integer sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 状态
     */
    private SwitchStatus status;

    /**
     * 排序
     */
    private Integer sort;
}
