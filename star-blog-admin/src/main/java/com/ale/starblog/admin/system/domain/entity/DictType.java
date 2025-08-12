package com.ale.starblog.admin.system.domain.entity;


import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 字典类型
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 10:51
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_dict_type")
@EqualsAndHashCode(callSuper = true)
public class DictType extends BaseAuditEntity {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;
}
