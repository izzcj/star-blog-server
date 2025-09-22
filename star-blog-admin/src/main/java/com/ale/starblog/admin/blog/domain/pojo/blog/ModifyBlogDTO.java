package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.admin.blog.enums.BlogStatus;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 修改博客DTO
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
public class ModifyBlogDTO extends BaseModifyDTO {

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
     * 状态
     */
    private BlogStatus status;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;
}