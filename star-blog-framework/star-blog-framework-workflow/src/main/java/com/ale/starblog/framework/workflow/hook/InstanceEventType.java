package com.ale.starblog.framework.workflow.hook;

import com.ale.starblog.framework.common.enumeration.BaseEnum;

/**
 * 流程实例事件类型
 *
 * @author Ale
 * @version 1.0.0 2025/6/12 14:53
 */
public enum InstanceEventType implements BaseEnum<String> {

    /**
     * 启动之前
     */
    BEFORE_START,

    /**
     * 启动之后
     */
    AFTER_START,

    /**
     * 更新之前
     */
    BEFORE_UPDATE,

    /**
     * 更新之后
     */
    AFTER_UPDATE,

    /**
     * 撤回之前
     */
    BEFORE_REVOKE,

    /**
     * 撤回之后
     */
    AFTER_REVOKE,

    /**
     * 回退之前
     */
    BEFORE_ROLLBACK,

    /**
     * 回退之后
     */
    AFTER_ROLLBACK,

    /**
     * 驳回之前
     */
    BEFORE_REJECT,

    /**
     * 驳回之后
     */
    AFTER_REJECT,

    /**
     * 超时之前
     */
    BEFORE_TIMEOUT,

    /**
     * 超时之后
     */
    AFTER_TIMEOUT,

    /**
     * 结束之前
     */
    BEFORE_COMPLETE,

    /**
     * 结束之后
     */
    AFTER_COMPLETE;

    InstanceEventType() {
        this.init();
    }
}
