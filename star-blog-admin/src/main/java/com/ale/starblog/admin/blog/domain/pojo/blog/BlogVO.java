package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.admin.blog.domain.pojo.tag.BlogTagVO;
import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客VO
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
public class BlogVO extends BaseVO {

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
     * 状态 (0:草稿, 1:已发布)
     */
    private Integer status;

    /**
     * 是否置顶 (0:否, 1:是)
     */
    private Boolean isTop;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 标签列表
     */
    private List<BlogTagVO> tags;
}