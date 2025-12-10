package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.system.domain.entity.DailyStatInfo;
import com.ale.starblog.admin.system.mapper.DailyStatInfoMapper;
import com.ale.starblog.admin.system.service.IDailyStatInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 每日统计信息服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:33
 */
@Service
public class DailyStatInfoServiceImpl extends ServiceImpl<DailyStatInfoMapper, DailyStatInfo> implements IDailyStatInfoService {
}
