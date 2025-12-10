package com.ale.starblog.admin.blog.domain.pojo.activity;

import com.ale.starblog.admin.blog.enums.ActivityType;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.framework.core.translation.TranslationField;
import com.ale.starblog.framework.core.translation.TranslationFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 动态VO
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 动态类型
     */
    private ActivityType type;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 引用id
     */
    private Long refId;

    /**
     * 用户id
     */
    @TranslationFields({
        @TranslationField(type = TranslationConstants.TRANSLATION_USER_NICKNAME, field = "userNickname"),
        @TranslationField(type = TranslationConstants.TRANSLATION_USER_AVATAR, field = "userAvatar")
    })
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
