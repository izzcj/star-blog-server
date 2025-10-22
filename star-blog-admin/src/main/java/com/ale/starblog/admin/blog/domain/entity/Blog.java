package com.ale.starblog.admin.blog.domain.entity;

import com.ale.starblog.admin.blog.enums.BlogStatus;
import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.core.oss.OssUpload;
import com.ale.starblog.framework.core.oss.RichText;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 博客实体类
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
@TableName("blog")
@EqualsAndHashCode(callSuper = true)
public class Blog extends BaseAuditEntity {

    /**
     * 分类
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @RichText
    @OssUpload
    private String content;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图片
     */
    @OssUpload
    private String coverImage;

    /**
     * 浏览量
     */
    private Long viewCount;

    /**
     * 状态
     */
    private BlogStatus status;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
}