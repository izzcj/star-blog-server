package com.ale.starblog.framework.core.query.condition;

import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

/**
 * 不为空
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/12
 */
@Component
public class IsNotNullCondition implements QueryCondition {

    @Override
    public void build(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper, QueryParameter[] parameters) {
        queryWrapper.isNotNull(fieldName);
    }

    @Override
    public QueryType provideQueryType() {
        return QueryType.IS_NOT_NULL;
    }
}
