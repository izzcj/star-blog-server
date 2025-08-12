package com.ale.starblog.admin.blog.domain.pojo.tag;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 标签查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagQuery extends BaseQuery {

    /**
     * 标签名称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String name;
}