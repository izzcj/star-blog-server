package com.ale.starblog.admin.system.domain.pojo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 修改用户密码DTO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/24
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserPasswordDTO {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
