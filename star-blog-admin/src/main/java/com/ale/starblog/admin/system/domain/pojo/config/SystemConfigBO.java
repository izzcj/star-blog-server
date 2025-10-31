package com.ale.starblog.admin.system.domain.pojo.config;

import com.ale.starblog.admin.system.enums.SystemConfigDataSourceType;
import com.ale.starblog.admin.system.enums.SystemConfigType;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统配置BO
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemConfigBO extends BaseBO {

    /**
     * 分类
     */
    private String category;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private SystemConfigType type;

    /**
     * key
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 数据源类型
     */
    private SystemConfigDataSourceType dataSourceType;

    /**
     * 数据配置
     */
    private String dataSourceConfig;
}