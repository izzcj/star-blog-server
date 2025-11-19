package com.ale.starblog.admin.system.domain.pojo.menu;

import com.ale.starblog.framework.common.support.TreeNode;
import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

/**
 * 菜单BO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuBO extends BaseBO implements TreeNode<MenuBO> {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单uri
     */
    private String uri;

    /**
     * 组件名称
     */
    private String component;

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
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 路由参数
     */
    private List<String> params;

    /**
     * 子菜单
     */
    private Collection<MenuBO> children;

    @Override
    public void setChildren(Collection<MenuBO> children) {
        this.children = children;
    }

    @Override
    public Collection<MenuBO> getChildren() {
        return this.children;
    }
}
