package com.ale.starblog.framework.workflow.enumeration;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import lombok.Getter;

/**
 * 多人审批方式
 *
 * @author Ale
 * @version 1.0.0 2025/6/23 16:45
 */
@Getter
public enum MultiApprovalStrategy implements BaseEnum<String> {

    /**
     * 顺签
     */
    SEQUENTIAL,

    /**
     * 会签
     */
    JOINT,

    /**
     * 或签
     */
    ALTERNATIVE,

    /**
     * 票签
     */
    VOTE;

    MultiApprovalStrategy() {
        this.init();
    }
}
