package com.ale.starblog.admin.system.domain.pojo.stat_info.daily;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 每日统计信息查询参数
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:29
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DailyStatInfoQuery extends BaseQuery {

    /**
     * 统计日期
     */
    @Query
    private LocalDate statDate;

}
