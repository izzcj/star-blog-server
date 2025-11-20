package com.ale.starblog.admin.system.domain.pojo.role;

import com.ale.starblog.admin.system.enums.RoleType;
import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 角色VO
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
public class RoleVO extends BaseVO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色类型
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
