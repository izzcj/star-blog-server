package com.ale.starblog.admin.system.domain.pojo.dict.type;

import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 创建字典类型DTO
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateDictTypeDTO extends BaseCreateDTO {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

}
