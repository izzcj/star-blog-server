package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.admin.blog.enums.ActivityType;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 动态实体类
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("blog_activity")
@EqualsAndHashCode(callSuper = true)
public class Activity extends BaseEntity {

    /**
     * 动态类型
     */
    private ActivityType type;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 引用id
     */
    private Long refId;

    /**
     * 用户id
     */
    private Long userId;
}
