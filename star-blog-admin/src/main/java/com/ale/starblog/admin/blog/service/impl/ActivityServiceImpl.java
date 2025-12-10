package com.ale.starblog.admin.blog.service.impl;

import com.ale.starblog.admin.blog.domain.entity.Activity;
import com.ale.starblog.admin.blog.mapper.ActivityMapper;
import com.ale.starblog.admin.blog.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 动态服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:36
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
}
