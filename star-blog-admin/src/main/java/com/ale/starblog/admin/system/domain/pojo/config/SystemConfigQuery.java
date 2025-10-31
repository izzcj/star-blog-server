package com.ale.starblog.admin.system.domain.pojo.config;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统配置查询条件
 *
 * @author Ale
 * @version 1.0.0 2025/10/31 11:31
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SystemConfigQuery extends BaseQuery {

    /**
     * 分类
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String category;

    /**
     * 名称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String name;

    /**
     * key
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String key;
}