package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 节点执行状态
 *
 * @author Ale
 * @version 1.0.0 2025/7/18 11:25
 */
@Getter
public enum NodeExecuteState implements BaseEnum<String> {

    /**
     * 未执行
     */
    NOT_EXECUTED("未执行"),

    /**
     * 执行中
     */
    EXECUTING("执行中"),

    /**
     * 已执行
     */
    EXECUTED("已执行");

    /**
     * 名称
     */
    private final String name;

    NodeExecuteState(String name) {
        this.name = name;
        this.init(this.toString(), name);
    }
}
