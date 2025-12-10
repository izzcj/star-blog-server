package com.ale.starblog.admin.blog.domain.pojo.activity;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 动态查询条件
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:53
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActivityQuery extends BaseQuery {

    /**
     * 排序字段
     */
    @Query(type = QueryType.SORT, parameters = @QueryParameter(name = "order", value = "desc"))
    private final String sort = "createTime";

}
