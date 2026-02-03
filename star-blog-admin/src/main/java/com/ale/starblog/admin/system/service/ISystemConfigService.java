package com.ale.starblog.admin.system.service;

import com.ale.starblog.admin.system.domain.entity.SystemConfig;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigBO;
import com.ale.starblog.admin.system.domain.pojo.config.SystemConfigQuery;
import com.ale.starblog.framework.core.service.ICrudService;

/**
 * 系统配置服务接口
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
public interface ISystemConfigService extends ICrudService<SystemConfig, SystemConfigBO, SystemConfigQuery> {

    /**
     * 获取配置值
     *
     * @param key       配置键
     * @param parseJson 是否解析json
     * @param <T>       值类型
     * @return 配置值
     */
    <T> T fetchValueByKey(String key, boolean parseJson);

    /**
     * 获取配置值
     *
     * @param key 配置键
     * @param <T>       值类型
     * @return 配置值
     */
    default <T> T fetchValueByKey(String key) {
        return fetchValueByKey(key, true);
    }

}