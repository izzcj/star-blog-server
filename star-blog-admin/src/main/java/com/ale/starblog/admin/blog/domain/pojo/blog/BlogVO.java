package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.admin.blog.domain.pojo.tag.BlogTagVO;
import com.ale.starblog.admin.blog.enums.BlogStatus;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.framework.core.pojo.BaseVO;
import com.ale.starblog.framework.core.translation.TranslationField;
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
     * 分类
     */
    @TranslationField(type = TranslationConstants.TRANSLATION_DICT, params = "type=blog_type")
    private String type;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 标题
     */
    private String title;

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
     * 标签列表
     */
    private List<BlogTagVO> tags;
}