package com.ale.starblog.admin.blog.enums;

import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.ale.starblog.framework.common.support.Comment;
import lombok.Getter;

/**
 * 动态类型
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:03
 */
@Getter
@Comment("动态类型")
public enum ActivityType implements BaseEnum<String> {

    /**
     * 发布文章
     */
    ARTICLE,

    /**
     * 发表评论
     */
    COMMENT,

    /**
     * 回复评论
     */
    REPLY;

    ActivityType() {
        this.init();
    }
}
