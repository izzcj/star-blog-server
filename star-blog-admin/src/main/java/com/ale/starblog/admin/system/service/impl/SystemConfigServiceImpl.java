package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigBO;
import com.ale.starblog.admin.system.mapper.SystemConfigMapper;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统配置服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@Service
public class SystemConfigServiceImpl extends AbstractCrudServiceImpl<SystemConfigMapper, SystemConfig, SystemConfigBO> implements ISystemConfigService {
}