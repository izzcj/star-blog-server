package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.framework.core.pojo.BaseVO;
import com.ale.starblog.framework.core.translation.TranslationField;
import com.ale.starblog.framework.core.translation.TranslationFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 评论VO
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:35
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentVO extends BaseVO {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 父级评论ID
     */
    private Long parentId;

    /**
     * 根评论ID
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 当前用户是否已点赞
     */
    private Boolean liked;

    /**
     * 状态
     */
    private CommentStatus status;

    /**
     * 评论用户ID
     */
    @TranslationFields({
        @TranslationField(type = TranslationConstants.TRANSLATION_USER_NICKNAME, field = "userNickname"),
        @TranslationField(type = TranslationConstants.TRANSLATION_USER_AVATAR, field = "userAvatar")
    })
    private Long userId;

    /**
     * 评论用户昵称
     */
    private String userNickname;

    /**
     * 评论用户头像
     */
    private String userAvatar;

    /**
     * 回复目标用户ID
     */
    @TranslationField(type = TranslationConstants.TRANSLATION_USER_NICKNAME, field = "replyUserNickname")
    private Long replyUserId;

    /**
     * 回复目标用户昵称
     */
    private String replyUserNickname;

    /**
     * 回复数
     */
    private Integer replyCount;

}
