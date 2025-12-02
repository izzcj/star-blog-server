package com.ale.starblog.admin.blog.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 文章状态
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Getter
@Comment("文章状态")
public enum ArticleStatus implements BaseEnum<String> {

    /**
     * 草稿
     */
    DRAFT("draft", "草稿"),

    /**
     * 待审核
     */
    WAIT_AUDIT("wait_audit", "待审核"),

    /**
     * 审核未通过
     */
    REJECT("reject", "审核未通过"),

    /**
     * 审核通过
     */
    PASS("pass", "审核通过"),

    /**
     * 已发布
     */
    PUBLISHED("published", "已发布");

    ArticleStatus(String value, String msg) {
        this.init(value, msg);
    }
}