package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.admin.system.enums.RoleType;
import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseAuditEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private RoleType type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否为默认角色
     */
    private Boolean defaultRole;

}
