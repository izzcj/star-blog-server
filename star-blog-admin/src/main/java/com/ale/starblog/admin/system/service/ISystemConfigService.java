package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.CreateSystemConfigDTO;
import com.ale.starblog.admin.system.domain.pojo.config.ModifySystemConfigDTO;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigBO;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 系统配置服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
public interface ISystemConfigService extends ICrudService<SystemConfig, SystemConfigBO, CreateSystemConfigDTO, ModifySystemConfigDTO> {
}