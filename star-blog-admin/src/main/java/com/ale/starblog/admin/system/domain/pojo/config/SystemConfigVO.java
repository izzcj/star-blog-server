package com.ale.starblog.admin.system.domain.pojo.config;

import com.ale.starblog.admin.system.enums.SystemConfigDataSourceType;
import com.ale.starblog.admin.system.enums.SystemConfigType;
import com.ale.starblog.framework.core.pojo.BaseVO;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.framework.core.translation.TranslationField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统配置VO
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemConfigVO extends BaseVO {

    /**
     * 分类
     */
    @TranslationField(type = TranslationConstants.TRANSLATION_DICT, params = "type=system-config-category")
    private String category;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 排序
     */
    private Integer sort;

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
     * 数据源配置
     */
    private String dataSourceConfig;

    /**
     * 是否可删除
     */
    private Boolean deletable;
}