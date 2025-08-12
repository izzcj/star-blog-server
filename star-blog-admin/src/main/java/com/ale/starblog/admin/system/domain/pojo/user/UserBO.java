package com.ale.starblog.admin.system.domain.pojo.user;

import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
 * 用户BO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

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
