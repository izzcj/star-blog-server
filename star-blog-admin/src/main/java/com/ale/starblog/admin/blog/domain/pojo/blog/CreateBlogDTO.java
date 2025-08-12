package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 创建博客DTO
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
public class CreateBlogDTO extends BaseCreateDTO {

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
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
     * 状态 (0:草稿, 1:已发布)
     */
    private Integer status;

    /**
     * 是否置顶 (0:否, 1:是)
     */
    private Boolean isTop;

    /**
     * 标签ID列表
     */
    private List<Long> tagIds;
}