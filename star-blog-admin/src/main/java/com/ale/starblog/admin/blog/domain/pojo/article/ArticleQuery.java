package com.ale.starblog.admin.blog.domain.pojo.article;

import com.ale.starblog.admin.blog.enums.ArticleStatus;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文章查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/8/12
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArticleQuery extends BaseQuery {

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
    private ArticleStatus status;

    /**
     * 是否置顶
     */
    @Query
    private Boolean isTop;

    /**
     * 排序字段
     */
    @Builder.Default
    @Query(type = QueryType.SORT, parameters = @QueryParameter(name = "order", value = "desc"))
    private String sort = "createTime";
}