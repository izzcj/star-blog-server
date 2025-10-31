package com.ale.starblog.admin.system.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 系统配置类型
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:03
 */
@Getter
@Comment("系统配置类型")
public enum SystemConfigType implements BaseEnum<String> {

    /**
     * 文本
     */
    TEXT("文本"),

    /**
     * 文本域
     */
    TEXTAREA("文本域"),

    /**
     * 数字
     */
    NUMBER("数字"),

    /**
     * 布尔
     */
    BOOLEAN("布尔"),

    /**
     * 下拉单选
     */
    SELECT("下拉单选"),

    /**
     * 下拉多选
     */
    MULTI_SELECT("下拉多选"),

    /**
     * 单选
     */
    RADIO("单选"),

    /**
     * 多选
     */
    CHECKBOX("多选"),

    /**
     * 日期时间
     */
    DATE_TIME("日期时间"),

    /**
     * 日期时间范围
     */
    DATE_TIME_RANGE("日期时间范围"),

    /**
     * 日期
     */
    DATE("日期"),

    /**
     * 日期范围
     */
    DATE_RANGE("日期范围"),

    /**
     * 时间
     */
    TIME("时间"),

    /**
     * 时间范围
     */
    TIME_RANGE("时间范围"),

    /**
     * JSON
     */
    JSON("JSON"),

    /**
     * 图片
     */
    IMAGE("图片");

    SystemConfigType(String msg) {
        this.init(this.toString(), msg);
    }

}
