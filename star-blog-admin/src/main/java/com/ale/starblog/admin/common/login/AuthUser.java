package com.ale.starblog.admin.common.login;

import com.ale.starblog.framework.common.security.AuthenticatedUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * 认证用户
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/8/13
 **/
@Data
@Builder
public class AuthUser implements AuthenticatedUser {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否为管理员
     */
    private Boolean admin;

    /**
     * 用户角色ID集合
     */
    private Set<Long> roleIds;

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
