package com.ale.starblog.admin.blog.mapper;

import com.ale.starblog.admin.blog.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签mapper
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}