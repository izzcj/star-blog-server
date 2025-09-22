package com.ale.starblog.admin.blog.domain.pojo.blog;

import com.ale.starblog.admin.blog.enums.BlogStatus;
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
     * 分类
     */
    @Query
    private String type;

    /**
     * 标题
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String title;

    /**
     * 状态
     */
    @Query
    private BlogStatus status;

    /**
     * 是否置顶
     */
    @Query
    private Boolean isTop;
}