package com.ale.starblog.admin.system.domain.pojo.dict.data;

import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 字典数据VO
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:19
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictDataVO extends BaseVO {

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
     * 是否可删除
     */
    private Boolean deletable;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
