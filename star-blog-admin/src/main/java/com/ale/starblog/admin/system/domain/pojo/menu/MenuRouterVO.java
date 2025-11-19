package com.ale.starblog.admin.system.domain.pojo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

/**
 * 菜单路由VO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/18
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRouterVO {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 组件名
     */
    private String component;

    /**
     * 名称
     */
    private String name;

    /**
     * uri
     */
    private String uri;

    /**
     * 重定向地址
     */
    private String redirectUri;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否为一级菜单
     */
    private Boolean topLevel;

    /**
     * 是否keep-alive缓存
     */
    private Boolean keepAlive;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 路由参数
     */
    private List<String> params;

    /**
     * 子菜单
     */
    private Collection<MenuRouterVO> children;

}
