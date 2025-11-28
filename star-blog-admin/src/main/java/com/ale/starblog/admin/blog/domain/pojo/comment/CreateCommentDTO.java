package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 创建评论DTO
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:37
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateCommentDTO extends BaseCreateDTO {

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    /**
     * 父级评论ID（可空，空表示顶级评论）
     */
    private Long parentId;

    /**
     * 回复目标用户ID（可空）
     */
    private Long replyUserId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 500, message = "评论内容不能超过500字")
    private String content;
}
