package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 流程执行状态
 *
 * @author Ale
 * @version 1.0.0 2025/7/24 14:35
 */
@Getter
public enum FlowExecutionState implements BaseEnum<String> {

    /**
     * 激活
     */
    ACTIVE("激活"),

    /**
     * 挂起
     */
    SUSPEND("挂起"),

    /**
     * 完成
     */
    COMPLETE("完成");

    /**
     * 名称
     */
    private final String name;

    FlowExecutionState(String name) {
        this.name = name;
        this.init(this.toString(), name);
    }

}
