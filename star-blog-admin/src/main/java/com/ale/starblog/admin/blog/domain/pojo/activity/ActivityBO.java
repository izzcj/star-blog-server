package com.ale.starblog.admin.blog.domain.pojo.activity;

import com.ale.starblog.admin.blog.domain.entity.Activity;
import com.ale.starblog.admin.blog.domain.entity.Article;
import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 动态BO
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBO {

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
     * 引用id
     */
    private Long refId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 转换为实体
     *
     * @return 实体
     */
    public Activity toEntity() {
        return Activity.builder()
            .type(this.type)
            .content(this.content)
            .articleId(this.articleId)
            .refId(this.refId)
            .userId(this.userId)
            .build();
    }

    /**
     * 将文章转换为动态
     *
     * @param article 文章
     * @return 动态
     */
    public static ActivityBO convertFromArticle(Article article) {
        return ActivityBO.builder()
            .type(ActivityType.ARTICLE)
            .content("发布了新文章")
            .articleId(article.getId())
            .refId(article.getId())
            .userId(article.getCreateBy())
            .createTime(article.getCreateTime())
            .build();
    }

    /**
     * 将评论转换为动态
     *
     * @param comment 评论
     * @return 动态
     */
    public static ActivityBO convertFromComment(Comment comment) {
        return ActivityBO.builder()
            .type(comment.getReplyUserId() == null ? ActivityType.COMMENT : ActivityType.REPLY)
            .content(comment.getContent())
            .articleId(comment.getArticleId())
            .refId(comment.getId())
            .userId(comment.getUserId())
            .createTime(comment.getCreateTime())
            .build();
    }
}
