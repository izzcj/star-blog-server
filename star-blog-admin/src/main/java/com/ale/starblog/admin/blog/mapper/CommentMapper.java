package com.ale.starblog.admin.blog.mapper;

import com.ale.starblog.admin.blog.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论Mapper
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:42
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
