package com.ale.starblog.admin.system.domain.pojo.dict.data;

import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 修改字典数据DTO
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyDictDataDTO extends BaseModifyDTO {

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

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
