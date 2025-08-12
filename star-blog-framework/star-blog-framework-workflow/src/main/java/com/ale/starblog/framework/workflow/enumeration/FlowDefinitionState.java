package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 流程定义状态
 *
 * @author Ale
 * @version 1.0.0 2025/6/5 11:45
 */
@Getter
public enum FlowDefinitionState implements BaseEnum<String> {

    /**
     * 激活
     */
    ACTIVE,

    /**
     * 挂起
     */
    SUSPENDED;

    FlowDefinitionState() {
        this.init();
    }
}
