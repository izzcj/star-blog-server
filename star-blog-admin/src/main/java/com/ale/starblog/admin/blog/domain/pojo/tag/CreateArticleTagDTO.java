package com.ale.starblog.admin.blog.domain.pojo.tag;

import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 创建文章标签DTO
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
public class CreateArticleTagDTO extends BaseCreateDTO {

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 标签颜色
     */
    private String color;
}