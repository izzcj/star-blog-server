package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 受理类型
 *
 * @author Ale
 * @version 1.0.0 2025/6/20 9:17
 */
@Getter
public enum AssigneeType implements BaseEnum<String> {

    /**
     * 转办
     */
    TRANSFER,

    /**
     * 委派
     */
    DELEGATE,

    /**
     * 认领
     */
    CLAIM;

    AssigneeType() {
        this.init();
    }
}
