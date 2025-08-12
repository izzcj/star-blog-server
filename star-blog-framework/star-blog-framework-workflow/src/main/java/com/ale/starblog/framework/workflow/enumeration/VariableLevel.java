package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 变量级别
 *
 * @author Ale
 * @version 1.0.0 2025/7/3 11:33
 */
@Getter
public enum VariableLevel implements BaseEnum<String> {

    /**
     * 实例级
     */
    INSTANCE,

    /**
     * 执行
     */
    EXECUTION,

    /**
     * 任务
     */
    TASK;

    VariableLevel() {
        this.init();
    }
}
