package com.ale.starblog.admin.blog.domain.pojo.tag;

import com.ale.starblog.framework.core.pojo.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 标签BO
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签图标
     */
    private String icon;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 标签颜色
     */
    private String color;
}