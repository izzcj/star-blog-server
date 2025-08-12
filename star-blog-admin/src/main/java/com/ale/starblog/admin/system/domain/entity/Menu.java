package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.admin.system.enums.MenuType;
import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseAuditEntity {

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 权限字符
     */
    private String perms;

    /**
     * 是否为外链
     */
    private Boolean frameFlag;

    /**
     * 是否缓存
     */
    private Boolean cacheFlag;

    /**
     * 菜单类型
     */
    private MenuType menuType;

    /**
     * 是否隐藏
     */
    private Boolean visible;

    /**
     * 状态
     */
    private SwitchStatus status;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

}
