package com.ale.starblog.admin.system.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/18
 */
@Getter
public enum MenuType implements BaseEnum<String> {

    /**
     * 目录
     */
    CATALOGUE("M", "目录"),

    /**
     * 菜单
     */
    MENU("C", "菜单"),

    /**
     * 按钮
     */
    BUTTON("B", "按钮");

    MenuType(String type, String msg) {
        this.init(type, msg);
    }
}
