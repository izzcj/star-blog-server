package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.CommentLike;
import com.ale.starblog.admin.blog.mapper.CommentLikeMapper;
import com.ale.starblog.admin.blog.service.ICommentLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 评论点赞关联服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 15:40
 */
@Service
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements ICommentLikeService {
}
