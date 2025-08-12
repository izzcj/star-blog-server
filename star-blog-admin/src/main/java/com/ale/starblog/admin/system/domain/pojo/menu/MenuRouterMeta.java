package com.ale.starblog.admin.system.domain.pojo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 菜单路由元信息
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 9:23
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRouterMeta {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    private String link;

}
