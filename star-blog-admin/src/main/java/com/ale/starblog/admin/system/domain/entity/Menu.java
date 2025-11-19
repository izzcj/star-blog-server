package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.core.mybatis.JsonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 菜单
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
@TableName(value = "sys_menu", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseAuditEntity {

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
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> params;

}
