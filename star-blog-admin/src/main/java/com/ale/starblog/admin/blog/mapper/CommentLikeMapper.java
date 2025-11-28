package com.ale.starblog.admin.blog.mapper;

import com.ale.starblog.admin.blog.domain.entity.CommentLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论点赞Mapper
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:45
 */
@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {
}
