package com.ale.starblog.admin.system.domain.pojo.dict.type;

import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 修改字典类型DTO
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyDictTypeDTO extends BaseModifyDTO {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典key
     */
    private String dictKey;

    /**
     * 排序
     */
    private Integer sort;

}
