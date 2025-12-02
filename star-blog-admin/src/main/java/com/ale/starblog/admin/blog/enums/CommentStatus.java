package com.ale.starblog.admin.blog.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 评论状态
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 15:01
 */
@Getter
@Comment("评论状态")
public enum CommentStatus implements BaseEnum<String> {

    /**
     * 待审核
     */
    PENDING("待审核"),

    /**
     * 通过
     */
    PASS("通过"),

    /**
     * 驳回
     */
    REJECT("驳回");

    CommentStatus(String msg) {
        this.init(this.toString(), msg);
    }
}
