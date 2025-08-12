package com.ale.starblog.framework.common.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 审计实体基类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/24
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
@EqualsAndHashCode(callSuper = true)
public class BaseAuditEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 5244624660683251996L;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 备注
     */
    private String remark;
}
