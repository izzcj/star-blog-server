package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户角色
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
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {

    /**
     * 系统用户id
     */
    private Long userId;

    /**
     * 系统角色id
     */
    private Long roleId;
}
