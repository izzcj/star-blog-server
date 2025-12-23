package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.*;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置接口
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController extends BaseController<SystemConfig, ISystemConfigService, SystemConfigVO, SystemConfigBO, SystemConfigQuery, CreateSystemConfigDTO, ModifySystemConfigDTO> {
}