package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量审核DTO
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchAuditDTO {

    /**
     * 评论ID列表
     */
    @NotEmpty(message = "评论ID列表不能为空")
    private List<Long> ids;

    /**
     * 审核状态
     */
    @NotNull(message = "审核状态不能为空")
    private CommentStatus status;
}
