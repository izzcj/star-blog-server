package com.ale.starblog.admin.blog.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.ale.starblog.admin.blog.domain.entity.CommentLike;
import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityBO;
import com.ale.starblog.admin.blog.domain.pojo.comment.CommentBO;
import com.ale.starblog.admin.blog.domain.pojo.comment.CommentQuery;
import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.admin.blog.listener.ActivityPublishedEvent;
import com.ale.starblog.admin.blog.mapper.CommentMapper;
import com.ale.starblog.admin.blog.service.ICommentLikeService;
import com.ale.starblog.admin.blog.service.ICommentService;
import com.ale.starblog.admin.system.constants.SystemConfigConstants;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.query.QueryConditionResolver;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Sets;
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

    /**
     * 系统配置服务
     */
    private final ISystemConfigService systemConfigService;

    @Override
    public void beforeCreate(Comment entity, HookContext context) {
        if (entity.getParentId() != null) {
            Comment parent = this.getById(entity.getParentId());
            if (parent == null) {
                throw new ServiceException("父评论不存在");
            }
            // 如果父评论是顶级评论，rootId = parentId；否则继承父评论的rootId
            entity.setRootId(parent.getRootId() != 0L ? parent.getRootId() : parent.getId());
        } else {
            entity.setParentId(0L);
            entity.setRootId(0L);
        }

        if (entity.getStatus() == null) {
            Boolean enableCommentAudit = this.systemConfigService.fetchValueByKey(SystemConfigConstants.ENABLE_COMMENT_AUDIT);
            if (BooleanUtil.isTrue(enableCommentAudit)) {
                entity.setStatus(CommentStatus.PENDING);
            } else {
                entity.setStatus(CommentStatus.PASS);
            }
        }
        if (entity.getLikeCount() == null) {
            entity.setLikeCount(0);
        }

        entity.setUserId(SecurityUtils.getLoginUserId());
    }

    @Override
    public void afterCreate(Comment entity, HookContext context) {
        // 发布动态
        SpringUtil.publishEvent(new ActivityPublishedEvent(this, ActivityBO.convertFromComment(entity)));
    }

    @Override
    public void beforeDelete(Comment entity, HookContext context) {
        // 删除关联评论
        this.remove(
            Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getRootId, entity.getId())
                .or()
                .eq(Comment::getReplyUserId, entity.getUserId())
        );
    }

    @Override
    public IPage<CommentBO> fetchPage(Pageable pageable, CommentQuery query) {
        LambdaQueryWrapper<Comment> queryWrapper = QueryConditionResolver.resolveLambda(query);

        IPage<CommentBO> result = this.executeQueryPage(pageable, queryWrapper, CommentBO.class);
        List<CommentBO> records = result.getRecords();

        Long loginUserId = SecurityUtils.getLoginUserId();
        if (records.isEmpty()) {
            return result;
        }
        Set<Long> commentIds = records.stream()
            .map(CommentBO::getId)
            .collect(Collectors.toSet());

        Set<Long> loginUserLikedCommentIds = Sets.newHashSet();
        if (loginUserId != null) {
            loginUserLikedCommentIds.addAll(this.commentLikeService.lambdaQuery()
                .eq(CommentLike::getUserId, loginUserId)
                .in(CommentLike::getCommentId, commentIds)
                .list()
                .stream()
                .map(CommentLike::getCommentId)
                .collect(Collectors.toSet())
            );
        }

        Map<Long, Long> replyCountMapping = this.lambdaQuery()
            .select(Comment::getId, Comment::getRootId)
            .in(Comment::getRootId, commentIds)
            .list()
            .stream()
            .collect(Collectors.groupingBy(Comment::getRootId, Collectors.counting()));

        records.forEach(comment -> {
            comment.setLiked(loginUserLikedCommentIds.contains(comment.getId()));
            comment.setReplyCount(replyCountMapping.getOrDefault(comment.getId(), 0L).intValue());
        });
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
            .set(Comment::getLikeCount, comment.getLikeCount() + 1)
            .eq(Comment::getId, commentId)
            .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long commentId, Long userId) {
        // 1. 检查评论是否存在
        Comment comment = this.getById(commentId);
        if (comment == null) {
            throw new ServiceException("评论不存在");
        }

        // 2. 删除点赞记录
        boolean deleted = this.commentLikeService.remove(
            Wrappers.<CommentLike>lambdaQuery()
                .eq(CommentLike::getCommentId, commentId)
                .eq(CommentLike::getUserId, userId)
        );

        if (!deleted) {
            throw new ServiceException("未点赞，无法取消");
        }

        int likeCount = Math.max(comment.getLikeCount() - 1, 0);

        // 3. 更新点赞数
        this.lambdaUpdate()
            .set(Comment::getLikeCount, likeCount)
            .eq(Comment::getId, commentId)
            .update();
    }

    @Override
    public void batchAuditComments(List<Long> ids, CommentStatus status, String rejectReason) {
        if (status != CommentStatus.PASS && status != CommentStatus.REJECT) {
            throw new ServiceException("审核状态只能是通过或拒绝");
        }

        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("评论ID列表不能为空");
        }

        this.lambdaUpdate()
            .set(Comment::getStatus, status)
            .set(StrUtil.isNotBlank(rejectReason), Comment::getRejectReason, rejectReason)
            .in(Comment::getId, ids)
            .update();
    }
}
