package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.admin.common.annotations.StatInfo;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 评价
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 14:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("blog_comment")
@EqualsAndHashCode(callSuper = true)
@StatInfo(type = StatInfoConstants.STAT_INFO_TYPE_COMMENT_COUNT)
public class Comment extends BaseEntity {

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 父级评论ID
     */
    private Long parentId;

    /**
     * 根评论ID（性能优化字段）
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
     * 状态
     */
    private CommentStatus status;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 回复目标用户ID
     */
    private Long replyUserId;

    /**
     * 驳回理由
     */
    private String rejectReason;
}
