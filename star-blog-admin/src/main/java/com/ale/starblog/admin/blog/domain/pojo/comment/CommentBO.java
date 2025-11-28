package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 评论BO
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:36
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

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
     * 排序权重
     */
    private Integer sort;

    /**
     * 状态
     */
    private CommentStatus status;

    /**
     * 当前用户是否已点赞
     */
    private Boolean liked;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 回复目标用户ID
     */
    private Long replyUserId;
}
