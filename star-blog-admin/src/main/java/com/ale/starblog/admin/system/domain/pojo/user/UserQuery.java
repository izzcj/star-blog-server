package com.ale.starblog.admin.system.domain.pojo.user;

import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import com.ale.starblog.framework.core.query.QueryType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户查询条件
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {

    /**
     * 账号
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String account;

    /**
     * 昵称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String nickname;

    /**
     * 邮箱
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String email;

    /**
     * 状态
     */
    @Query
    private SwitchStatus status;

    /**
     * 排序字段
     */
    @Builder.Default
    @Query(type = QueryType.SORT)
    private String sort = "sort";

}
