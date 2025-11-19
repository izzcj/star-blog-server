package com.ale.starblog.admin.system.domain.pojo.menu;

import com.ale.starblog.framework.core.pojo.BaseModifyDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 修改菜单DTO
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
public class ModifyMenuDTO extends BaseModifyDTO {

    /**
     * 父菜单id
     */
    @NotNull(message = "父菜单id不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单uri
     */
    @NotBlank(message = "菜单uri不能为空")
    private String uri;

    /**
     * 组件名称
     */
    @NotBlank(message = "组件名称不能为空")
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

}
