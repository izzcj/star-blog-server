package com.ale.starblog.admin.system.domain.pojo.dict.type;

import com.ale.starblog.framework.core.query.BaseQuery;
import com.ale.starblog.framework.core.query.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 字典类型查询条件
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTypeQuery extends BaseQuery {

    /**
     * 字典名称
     */
    @Query
    private String dictName;

    /**
     * 字典类型
     */
    @Query
    private String dictType;

}
