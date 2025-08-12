package com.ale.starblog.admin.system.domain.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户个人信息VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/15
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVO {

    /**
     * 角色组
     * ','号分割
     */
    private String roleGroup;

    /**
     * 用户信息
     */
    private UserVO user;

}
