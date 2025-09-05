package com.ale.starblog.admin.system.support;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuBO;
import com.ale.starblog.admin.system.domain.pojo.menu.MenuRouterMeta;
import com.ale.starblog.admin.system.enums.MenuType;
import com.ale.starblog.framework.common.constants.HttpConstants;
import com.ale.starblog.framework.common.utils.TreeUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 菜单路由辅助类
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 9:20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MenuRouterHelper {

    /**
     * Layout组件标识
     */
    public static final String LAYOUT = "Layout";

    /**
     * ParentView组件标识
     */
    public static final String PARENT_VIEW = "ParentView";

    /**
     * InnerLink组件标识
     */
    public static final String INNER_LINK = "InnerLink";

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public static String getRouteName(MenuBO menu) {
        String routerName = StrUtil.upperFirst(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StrUtil.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(MenuBO menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId() != 0L && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        boolean isNoInnerLinkAndIsCatalogue = menu.getParentId() == 0L
            && Objects.equals(menu.getMenuType(), MenuType.CATALOGUE)
            && !menu.getFrameFlag();
        if (isNoInnerLinkAndIsCatalogue) {
            routerPath = "/" + menu.getPath();
        } else if (isMenuFrame(menu)) {
            // 非外链并且是一级目录（类型为菜单）
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public static String getComponent(MenuBO menu) {
        if (StrUtil.isNotEmpty(menu.getComponent())
            && !isMenuFrame(menu)) {
            return menu.getComponent();
        }
        if (StrUtil.isEmpty(menu.getComponent())
            && menu.getParentId() != 0L
            && isInnerLink(menu)) {
            return INNER_LINK;
        }
        if (StrUtil.isEmpty(menu.getComponent())
            && menu.getParentId() != 0L
            && Objects.equals(menu.getMenuType(), MenuType.CATALOGUE)) {
            return PARENT_VIEW;
        }
        return LAYOUT;
    }

    /**
     * 构建路由元信息
     *
     * @param menu 菜单信息
     * @return 路由元信息
     */
    public static MenuRouterMeta buildMeta(MenuBO menu) {
        return MenuRouterMeta.builder()
            .title(menu.getMenuName())
            .icon(menu.getIcon())
            .noCache(menu.getCacheFlag())
            .link(
                isInnerLink(menu) ? menu.getPath() : null
            )
            .build();
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public static boolean isMenuFrame(MenuBO menu) {
        return menu.getParentId() == TreeUtils.DEFAULT_TOP_LEVEL_ID
            && Objects.equals(menu.getMenuType(), MenuType.MENU)
            && !menu.getFrameFlag();
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public static boolean isInnerLink(MenuBO menu) {
        return !menu.getFrameFlag() && StringUtils.startsWithAny(menu.getPath(), HttpConstants.HTTP, HttpConstants.HTTPS);
    }

    /**
     * 内链域名特殊字符替换
     *
     * @param path 地址
     * @return 替换后的内链域名
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(
            path,
            new String[] {HttpConstants.HTTP, HttpConstants.HTTPS, HttpConstants.WWW, "."},
            new String[] {"", "", "", "/"}
        );
    }

}
