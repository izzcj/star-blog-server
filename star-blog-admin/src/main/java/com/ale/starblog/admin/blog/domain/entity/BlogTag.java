package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 博客标签实体类
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
public class BlogTag extends BaseEntity {

    /**
     * 博客ID
     */
    private Long blogId;

    /**
     * 标签ID
     */
    private Long tagId;
}