package com.ale.starblog.framework.core.query.condition;

import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

/**
 * 大于等于
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/7
 */
@Component
public class GreaterThanOrEqualCondition implements QueryCondition {

    @Override
    public void build(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper, QueryParameter[] parameters) {
        queryWrapper.ge(
            fieldName,
            fieldValue
        );
    }

    @Override
    public QueryType provideQueryType() {
        return QueryType.GE;
    }
}
