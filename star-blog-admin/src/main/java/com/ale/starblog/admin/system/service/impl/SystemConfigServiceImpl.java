package com.ale.starblog.admin.system.service.impl;

import cn.hutool.json.JSONObject;
import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigBO;
import com.ale.starblog.admin.system.enums.SystemConfigType;
import com.ale.starblog.admin.system.mapper.SystemConfigMapper;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.oss.OssSupport;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 系统配置服务实现类
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@Service
public class SystemConfigServiceImpl extends AbstractCrudServiceImpl<SystemConfigMapper, SystemConfig, SystemConfigBO> implements ISystemConfigService {

    @Override
    public void beforeCreate(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            entity.setValue(OssSupport.moveObject(entity.getValue()));
        }
    }

    @Override
    public void beforeModify(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            SystemConfig oldConfig = context.get(HookConstants.OLD_ENTITY_KEY);
            if (Objects.equals(oldConfig.getValue(), entity.getValue())) {
                return;
            }
            OssSupport.removeObject(oldConfig.getValue());
            entity.setValue(OssSupport.moveObject(entity.getValue()));
        }
    }

    @Override
    public void afterDelete(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            OssSupport.removeObject(entity.getValue());
        }
    }

    @Override
    public Object fetchValueByKey(String key) {
        SystemConfig systemConfig = this.lambdaQuery()
            .eq(SystemConfig::getKey, key)
            .oneOpt()
            .orElseThrow(() -> new ServiceException("配置[{}]不存在！", key));
        return switch (systemConfig.getType()) {
            case SystemConfigType.CHECKBOX, SystemConfigType.MULTI_SELECT -> JsonUtils.fromJsonList(systemConfig.getValue(), String.class);
            case SystemConfigType.BOOLEAN -> Boolean.parseBoolean(systemConfig.getValue());
            case SystemConfigType.NUMBER -> new BigDecimal(systemConfig.getValue());
            case SystemConfigType.JSON -> JsonUtils.fromJson(systemConfig.getValue(), JSONObject.class);
            default -> systemConfig.getValue();
        };
    }
}