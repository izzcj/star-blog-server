package com.ale.starblog.admin.system.domain.entity;

import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 每日统计信息实体类
 *
 * @author Ale
 * @version 1.0.0 2025/12/9 14:02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_daily_stat_info")
@EqualsAndHashCode(callSuper = true)
public class DailyStatInfo extends BaseEntity {

    /**
     * 统计日期
     */
    private LocalDate statDate;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 注册用户量
     */
    private Integer registerCount;

    /**
     * 活跃用户量
     */
    private Integer activeCount;

}
