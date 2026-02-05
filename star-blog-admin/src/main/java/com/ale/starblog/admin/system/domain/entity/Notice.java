package com.ale.starblog.admin.system.domain.entity;

import java.time.LocalDateTime;

import com.ale.starblog.framework.common.domain.entity.BaseAuditEntity;
import com.ale.starblog.framework.core.oss.OssUpload;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 系统通知实体
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:15:51
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@TableName("sys_notice")
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseAuditEntity {

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
    @OssUpload(richText = true)
    private String content;

    /**
     * 是否发布
     */
    private Boolean published;

    /**
     * 发布时间
     */
    private LocalDateTime publishedTime;
}
