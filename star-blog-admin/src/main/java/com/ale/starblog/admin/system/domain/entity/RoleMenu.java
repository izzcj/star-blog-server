package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色菜单
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
