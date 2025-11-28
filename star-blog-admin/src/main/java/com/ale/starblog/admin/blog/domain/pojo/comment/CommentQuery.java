package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 评论查询条件
 *
 * @author Ale
 * @version 1.0.0 2025/11/27 16:39
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentQuery extends BaseQuery {

    /**
     * 文章ID
     */
    @Query
    private Long articleId;

    /**
     * 状态
     */
    @Query
    private CommentStatus status;

    /**
     * 用户ID
     */
    @Query
    private Long userId;

    /**
     * 排序字段
     */
    @Query(type = QueryType.SORT, parameters = @QueryParameter(name = "order", value = "desc"))
    private String sort = "create_time";
}
