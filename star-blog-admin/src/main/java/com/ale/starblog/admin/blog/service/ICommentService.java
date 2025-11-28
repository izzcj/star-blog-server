package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.domain.pojo.comment.CommentBO;
import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.framework.core.service.ICrudService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 评论服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:47
 */
public interface ICommentService extends ICrudService<Comment, CommentBO> {

    /**
     * 查询子评论
     *
     * @param pageable 分页参数
     * @param parentId 父评论ID
     * @return 子评论分页数据
     */
    IPage<CommentBO> fetchChildren(Pageable pageable, Long parentId);

    /**
     * 点赞评论
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     */
    void likeComment(Long commentId, Long userId);

    /**
     * 取消点赞
     *
     * @param commentId 评论ID
     * @param userId    用户ID
     */
    void unlikeComment(Long commentId, Long userId);

    /**
     * 审核评论
     *
     * @param commentId 评论ID
     * @param status    审核状态
     */
    void auditComment(Long commentId, CommentStatus status);

    /**
     * 批量审核评论
     *
     * @param ids    评论ID列表
     * @param status 审核状态
     */
    void batchAuditComments(List<Long> ids, CommentStatus status);
}
