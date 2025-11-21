package com.ale.starblog.admin.system.domain.pojo.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 授权用户角色dto
 *
 * @author Ale
 * @version 1.0.0 2025/5/28 17:37
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRoleDTO {

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    /**
     * 用户id集合
     */
    @NotEmpty(message = "用户id不能为空")
    private List<Long> userIds;

}
