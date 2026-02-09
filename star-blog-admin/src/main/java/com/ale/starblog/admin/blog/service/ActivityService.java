package com.ale.starblog.admin.blog.service;

import com.ale.starblog.admin.blog.domain.entity.Activity;
import com.ale.starblog.admin.blog.mapper.ActivityMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 动态服务
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:36
 */
@Service
public class ActivityService extends ServiceImpl<ActivityMapper, Activity> {
}
