package com.ale.starblog.admin.system.domain.pojo.role;

import com.ale.starblog.admin.system.enums.RoleType;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 角色查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/31
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleQuery extends BaseQuery {

    /**
     * 角色名称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String name;

    /**
     * 角色类型
     */
    @Query
    private RoleType type;
}
