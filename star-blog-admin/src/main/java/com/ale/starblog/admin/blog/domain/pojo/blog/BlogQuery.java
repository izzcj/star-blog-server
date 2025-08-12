package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 博客查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogQuery extends BaseQuery {

    /**
     * 标题
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String title;

    /**
     * 状态 (0:草稿, 1:已发布)
     */
    @Query
    private Integer status;

    /**
     * 是否置顶 (0:否, 1:是)
     */
    @Query
    private Boolean isTop;
}