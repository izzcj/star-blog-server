package com.ale.starblog.admin.system.domain.pojo.role;

import com.ale.starblog.admin.system.enums.RoleType;
import com.ale.starblog.framework.core.pojo.BaseCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 创建角色dto
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateRoleDTO extends BaseCreateDTO {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private RoleType roleType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否为默认角色
     */
    private Boolean defaultRole;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

}
