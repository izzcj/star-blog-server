package com.ale.starblog.admin.system.domain.pojo.user;

import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 修改用户DTO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/24
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyUserDTO extends BaseModifyDTO {

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 角色id
     */
    private List<Long> roleIds;
}
