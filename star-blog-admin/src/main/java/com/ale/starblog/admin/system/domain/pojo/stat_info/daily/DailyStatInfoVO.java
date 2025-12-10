package com.ale.starblog.admin.system.domain.pojo.stat_info.daily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 每日统计信息VO
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStatInfoVO {

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
