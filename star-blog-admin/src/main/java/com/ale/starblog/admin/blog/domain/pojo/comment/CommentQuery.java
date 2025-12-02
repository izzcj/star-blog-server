package com.ale.starblog.admin.blog.domain.pojo.comment;

import com.ale.starblog.admin.blog.enums.CommentStatus;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
     * 根评论ID
     */
    @Query
    private Long rootId;

    /**
     * 父评论ID
     */
    @Query
    private Long parentId;

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
     * 创建时间开始
     */
    @Query(column = "createTime", type = QueryType.GE)
    private LocalDateTime createTimeBegin;

    /**
     * 创建时间结束
     */
    @Query(column = "createTime", type = QueryType.LE)
    private LocalDateTime createTimeEnd;

    /**
     * 排序字段
     */
    @Builder.Default
    @Query(type = QueryType.SORT)
    private String sort = "createTime";
}
