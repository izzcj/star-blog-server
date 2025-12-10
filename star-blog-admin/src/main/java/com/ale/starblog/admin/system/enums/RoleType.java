package com.ale.starblog.admin.system.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 角色类型
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Getter
@Comment("角色类型")
public enum RoleType implements BaseEnum<String> {

    /**
     * 超级管理员
     */
    ADMIN("超级管理员"),

    /**
     * 普通管理员
     */
    COMMON_ADMIN("普通管理员"),

    /**
     * 普通用户
     */
    COMMON_USER("普通用户");

    RoleType(String msg) {
        this.init(this.toString(), msg);
    }
}
