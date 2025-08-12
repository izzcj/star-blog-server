package com.ale.starblog.admin.system.domain.pojo.menu;

import com.ale.starblog.admin.system.enums.MenuType;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 菜单VO
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
public class MenuVO extends BaseVO {

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
