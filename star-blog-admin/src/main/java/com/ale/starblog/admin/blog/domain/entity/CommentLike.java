package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 评论点赞关系
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("blog_comment_like")
@EqualsAndHashCode(callSuper = true)
public class CommentLike extends BaseEntity {

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 用户ID
     */
    private Long userId;
}
