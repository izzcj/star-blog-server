package com.ale.starblog.admin.system.domain.pojo.user;

import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.pojo.BaseVO;
import com.ale.starblog.framework.core.translation.TranslationField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 系统用户VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {

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
    @TranslationField(type = TranslationConstants.TRANSLATION_DICT, params = "type=sex")
    private String sex;

    /**
     * 性别名称
     */
    private String sexName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    private SwitchStatus status;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
}
