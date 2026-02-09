package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.CommentLike;
import com.ale.starblog.admin.blog.mapper.CommentLikeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 评论点赞关联服务
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 15:40
 */
@Service
public class CommentLikeService extends ServiceImpl<CommentLikeMapper, CommentLike> {
}
