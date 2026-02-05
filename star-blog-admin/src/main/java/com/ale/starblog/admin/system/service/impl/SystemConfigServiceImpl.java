package com.ale.starblog.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigBO;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigQuery;
import com.ale.starblog.admin.system.enums.SystemConfigType;
import com.ale.starblog.admin.system.mapper.SystemConfigMapper;
import com.ale.starblog.admin.system.service.ISystemConfigService;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.CastUtils;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.core.constants.HookConstants;
import com.ale.starblog.framework.core.oss.OssMate;
import com.ale.starblog.framework.core.oss.OssMateService;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SystemConfigServiceImpl extends AbstractCrudServiceImpl<SystemConfigMapper, SystemConfig, SystemConfigBO, SystemConfigQuery> implements ISystemConfigService {

    /**
     * Oss元信息服务
     */
    private final OssMateService ossMateService;

    @Override
    public void beforeCreate(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            OssMate ossMate = this.ossMateService.load(entity.getValue());
            if (ossMate == null) {
                throw new ServiceException("上传图片[{}]的元信息不存在！", entity.getValue());
            }
            ossMate.setReferenceCount(ossMate.getReferenceCount() + 1);
            this.ossMateService.update(ossMate);
        }
    }

    @Override
    public void beforeModify(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            SystemConfig oldConfig = context.get(HookConstants.OLD_ENTITY_KEY);
            if (Objects.equals(oldConfig.getValue(), entity.getValue())) {
                return;
            }
            if (StrUtil.isNotBlank(oldConfig.getValue())) {
                OssMate oldOssMate = this.ossMateService.load(oldConfig.getValue());
                if (oldOssMate == null) {
                    throw new ServiceException("图片[{}]的元信息不存在！", oldConfig.getValue());
                }
                oldOssMate.setReferenceCount(oldOssMate.getReferenceCount() - 1);
                this.ossMateService.update(oldOssMate);
            }
            if (StrUtil.isNotBlank(entity.getValue())) {
                OssMate ossMate = this.ossMateService.load(entity.getValue());
                if (ossMate == null) {
                    throw new ServiceException("图片[{}]的元信息不存在！", entity.getValue());
                }
                ossMate.setReferenceCount(ossMate.getReferenceCount() + 1);
                this.ossMateService.update(ossMate);
            }
        }
    }

    @Override
    public void afterDelete(SystemConfig entity, HookContext context) {
        if (SystemConfigType.IMAGE.match(entity.getType())) {
            OssMate ossMate = this.ossMateService.load(entity.getValue());
            if (ossMate == null) {
                throw new ServiceException("图片[{}]的元信息不存在！", entity.getValue());
            }
            ossMate.setReferenceCount(ossMate.getReferenceCount() - 1);
            this.ossMateService.update(ossMate);
        }
    }

    @Override
    public <T> T fetchValueByKey(String key, boolean parseJson) {
        SystemConfig systemConfig = this.lambdaQuery()
            .eq(SystemConfig::getKey, key)
            .oneOpt()
            .orElseThrow(() -> new ServiceException("配置[{}]不存在！", key));
        return CastUtils.cast(parseValue(systemConfig, parseJson));
    }

    /**
     * 解析值
     *
     * @param systemConfig 系统配置
     * @param parseJson    是否解析JSON
     * @return 解析后的值
     */
    private Object parseValue(SystemConfig systemConfig, boolean parseJson) {
        String value = systemConfig.getValue();
        return switch (systemConfig.getType()) {
            case SystemConfigType.CHECKBOX, SystemConfigType.MULTI_SELECT -> JsonUtils.fromJsonList(value, String.class);
            case SystemConfigType.BOOLEAN -> Boolean.parseBoolean(value);
            case SystemConfigType.NUMBER -> new BigDecimal(value);
            case SystemConfigType.JSON -> parseJson ? JsonUtils.fromJson(value, JSONObject.class) : value;
            default -> value;
        };
    }
}