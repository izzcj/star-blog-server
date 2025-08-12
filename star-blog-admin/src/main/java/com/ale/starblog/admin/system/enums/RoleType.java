package com.ale.starblog.admin.system.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 角色类型
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Getter
public enum RoleType implements BaseEnum<String> {

    /**
     * 超级管理员
     */
    ADMIN,

    /**
     * 普通用户
     */
    COMMON_USER,

    /**
     * 网站用户
     */
    WEB_USER;

    RoleType() {
        this.init();
    }
}
