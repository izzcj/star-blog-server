package com.ale.starblog.admin.blog.domain.pojo.article;

import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章BO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArticleBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

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
    private String content;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图片
     */
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

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;
}