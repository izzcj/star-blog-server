package com.ale.starblog.framework.core.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 创建实体DTO基类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseCreateDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 备注
     */
    private String remark;
}
