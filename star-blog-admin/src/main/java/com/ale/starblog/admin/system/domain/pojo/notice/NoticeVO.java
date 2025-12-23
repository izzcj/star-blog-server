package com.ale.starblog.admin.system.domain.pojo.notice;

import java.time.LocalDateTime;
import com.ale.starblog.framework.core.pojo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 系统公告VO
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:15:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends BaseVO {

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
