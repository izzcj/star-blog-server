package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.admin.system.enums.SystemConfigDataSourceType;
import com.ale.starblog.admin.system.enums.SystemConfigType;
import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 系统配置
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:00
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_config")
@EqualsAndHashCode(callSuper = true)
public class SystemConfig extends BaseAuditEntity {

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
