package com.ale.starblog.admin.blog.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;

/**
 * 评论状态
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 15:01
 */
public enum CommentStatus implements BaseEnum<String> {

    /**
     * 待审核
     */
    WAIT_AUDIT,

    /**
     * 审核通过
     */
    PASS,

    /**
     * 审核未通过
     */
    REJECT;

    CommentStatus() {
        this.init();
    }
}
