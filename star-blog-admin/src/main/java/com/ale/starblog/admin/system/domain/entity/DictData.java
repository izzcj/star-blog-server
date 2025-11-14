package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 字典数据
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_dict_data")
@EqualsAndHashCode(callSuper = true)
public class DictData extends BaseAuditEntity {

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典key
     */
    private String dictKey;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格字典样式
     */
    private String listClass;

    /**
     * 是否默认
     */
    private Boolean defaultFlag;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private SwitchStatus state;

}
