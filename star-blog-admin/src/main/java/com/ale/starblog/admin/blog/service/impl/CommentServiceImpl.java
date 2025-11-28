package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.domain.entity.CommentLike;
import com.ale.starblog.admin.blog.domain.pojo.comment.CommentBO;
import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.admin.blog.mapper.CommentMapper;
import com.ale.starblog.admin.blog.service.ICommentLikeService;
import com.ale.starblog.admin.blog.service.ICommentService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:50
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends AbstractCrudServiceImpl<CommentMapper, Comment, CommentBO> implements ICommentService {

    /**
     * 评论点赞服务
     */
    private final ICommentLikeService commentLikeService;

    @Override
    public void beforeCreate(Comment entity, HookContext context) {
        if (entity.getParentId() != null) {
            Comment parent = this.getById(entity.getParentId());
            if (parent == null) {
                throw new ServiceException("父评论不存在");
            }
            // 如果父评论是顶级评论，rootId = parentId；否则继承父评论的rootId
            entity.setRootId(parent.getRootId() != null ? parent.getRootId() : parent.getId());
        }

        if (entity.getStatus() == null) {
            entity.setStatus(CommentStatus.WAIT_AUDIT);
        }
        if (entity.getLikeCount() == null) {
            entity.setLikeCount(0);
        }
        if (entity.getSort() == null) {
            entity.setSort(0);
        }

        entity.setUserId(SecurityUtils.getLoginUserId());
    }

    @Override
    public IPage<CommentBO> fetchChildren(Pageable pageable, Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery()
            .eq(Comment::getId, parentId)
            .eq(Comment::getStatus, CommentStatus.PASS)
            .orderByDesc(Comment::getCreateTime);
        IPage<CommentBO> result = this.executeQueryPage(pageable, queryWrapper, CommentBO.class);
        List<CommentBO> records = result.getRecords();
        Long loginUserId = SecurityUtils.getLoginUserId();
        // 未登录不判断是否点赞
        if (records.isEmpty() || loginUserId == null) {
            return result;
        }
        Set<Long> commentIds = records.stream()
            .map(CommentBO::getId)
            .collect(Collectors.toSet());
        Set<Long> loginUserLikedCommentIds = this.commentLikeService.lambdaQuery()
            .eq(CommentLike::getUserId, loginUserId)
            .in(CommentLike::getCommentId, commentIds)
            .list()
            .stream()
            .map(CommentLike::getCommentId)
            .collect(Collectors.toSet());
        records.forEach(comment -> comment.setLiked(loginUserLikedCommentIds.contains(comment.getId())));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long commentId, Long userId) {
        // 1. 检查评论是否存在
        Comment comment = this.getById(commentId);
        if (comment == null) {
            throw new ServiceException("评论不存在");
        }

        // 2. 检查是否已点赞
        boolean liked = this.commentLikeService.lambdaQuery()
            .eq(CommentLike::getCommentId, commentId)
            .eq(CommentLike::getUserId, userId)
            .exists();
        if (liked) {
            throw new ServiceException("已点赞，请勿重复操作");
        }

        // 3. 插入点赞记录
        CommentLike like = CommentLike.builder()
            .commentId(commentId)
            .userId(userId)
            .build();
        this.commentLikeService.save(like);

        // 4. 更新点赞数
        this.lambdaUpdate()
            .setSql("like_count = like_count + 1")
            .eq(Comment::getId, commentId)
            .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long commentId, Long userId) {
        // 1. 删除点赞记录
        boolean deleted = this.commentLikeService.remove(
            Wrappers.<CommentLike>lambdaQuery()
                .eq(CommentLike::getCommentId, commentId)
                .eq(CommentLike::getUserId, userId)
        );

        if (!deleted) {
            throw new ServiceException("未点赞，无法取消");
        }

        // 2. 更新点赞数（防止负数）
        this.lambdaUpdate()
            .setSql("like_count = GREATEST(like_count - 1, 0)")
            .eq(Comment::getId, commentId)
            .update();
    }

    @Override
    public void auditComment(Long commentId, CommentStatus status) {
        if (status != CommentStatus.PASS && status != CommentStatus.REJECT) {
            throw new ServiceException("审核状态只能是通过或拒绝");
        }

        Comment comment = this.getById(commentId);
        if (comment == null) {
            throw new ServiceException("评论不存在");
        }

        this.lambdaUpdate()
            .set(Comment::getStatus, status)
            .eq(Comment::getId, commentId)
            .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAuditComments(List<Long> ids, CommentStatus status) {
        if (status != CommentStatus.PASS && status != CommentStatus.REJECT) {
            throw new ServiceException("审核状态只能是通过或拒绝");
        }

        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("评论ID列表不能为空");
        }

        this.lambdaUpdate()
            .set(Comment::getStatus, status)
            .in(Comment::getId, ids)
            .update();
    }
}
