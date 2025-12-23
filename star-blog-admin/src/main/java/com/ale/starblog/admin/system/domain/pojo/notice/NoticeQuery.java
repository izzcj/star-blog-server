package com.ale.starblog.admin.system.domain.pojo.notice;

import java.time.LocalDateTime;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 系统公告查询条件
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:15:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeQuery extends BaseQuery {

    /**
     * 标题
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String title;

    /**
     * 是否发布
     */
    @Query
    private Boolean published;

    /**
     * 发布时间
     */
    @Query
    private LocalDateTime publishedTime;
}
