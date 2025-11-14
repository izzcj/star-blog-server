package com.ale.starblog.admin.system.domain.pojo.dict.data;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 字典数据查询条件
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:35
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictDataQuery extends BaseQuery {

    /**
     * 字典名称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String dickName;

    /**
     * 字典key
     */
    @Query
    private String dictKey;

    /**
     * 排序字段
     */
    @Query(type = QueryType.SORT)
    private String sort = "sort";
}
