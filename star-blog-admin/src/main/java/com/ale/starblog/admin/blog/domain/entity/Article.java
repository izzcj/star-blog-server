package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.admin.common.annotations.StatInfo;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.core.oss.OssUpload;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 文章实体类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("blog_article")
@EqualsAndHashCode(callSuper = true)
@StatInfo(type = StatInfoConstants.STAT_INFO_TYPE_ARTICLE_COUNT)
public class Article extends BaseAuditEntity {

    /**
     * 分类
     */
    private String category;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @OssUpload(richText = true)
    private String content;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图片
     */
    @OssUpload
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String coverImage;

    /**
     * 浏览量
     */
    private Long viewCount;

    /**
     * 状态
     */
    private ArticleStatus status;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否推荐
     */
    private Boolean recommended;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
}