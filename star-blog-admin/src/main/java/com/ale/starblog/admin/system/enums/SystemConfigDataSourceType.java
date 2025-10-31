package com.ale.starblog.admin.system.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 系统配置数据源类型
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:48
 */
@Getter
@Comment("系统配置数据源类型")
public enum SystemConfigDataSourceType implements BaseEnum<String> {

    /**
     * 字典
     */
    DICT("字典"),

    /**
     * 枚举
     */
    ENUM("枚举"),

    /**
     * 数据接口
     */
    DATA_API("数据接口");

    SystemConfigDataSourceType(String msg) {
        this.init(this.toString(), msg);
    }

}
