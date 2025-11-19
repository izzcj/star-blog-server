package com.ale.starblog.admin.system.domain.pojo.menu;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 菜单查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuQuery extends BaseQuery {

    /**
     * 父菜单id
     */
    @Query
    private Long parentId;

    /**
     * 菜单名称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String name;

}
