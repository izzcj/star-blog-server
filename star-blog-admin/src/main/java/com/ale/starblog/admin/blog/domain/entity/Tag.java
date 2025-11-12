package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 标签实体类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("blog_tag")
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseAuditEntity {

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签描述
     */
    private String description;

    /**
     * 标签颜色
     */
    private String color;
}