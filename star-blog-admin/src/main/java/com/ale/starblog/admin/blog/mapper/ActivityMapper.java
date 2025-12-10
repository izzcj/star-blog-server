package com.ale.starblog.admin.blog.mapper;

import com.ale.starblog.admin.blog.domain.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态mapper
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:35
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}
