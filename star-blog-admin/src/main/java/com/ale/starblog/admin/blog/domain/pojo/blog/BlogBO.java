package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.admin.blog.enums.BlogStatus;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客BO
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
public class BlogBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

    /**
     * 分类
     */
    private String type;

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
    private BlogStatus status;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;
}