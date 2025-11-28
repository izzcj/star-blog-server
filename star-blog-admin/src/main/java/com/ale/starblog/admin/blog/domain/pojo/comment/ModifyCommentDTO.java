package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 修改评论DTO
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyCommentDTO extends BaseModifyDTO {

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Length(max = 500, message = "评论内容不能超过500字")
    private String content;

    /**
     * 状态（用于审核）
     */
    private CommentStatus status;
}
