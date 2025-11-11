package com.ale.starblog.framework.core.query.condition;

import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * IN
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/12
 */
@Component
public class InCondition implements QueryCondition {

    @SuppressWarnings("rawtypes")
    @Override
    public void build(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper, QueryParameter[] parameters) {
        if (fieldValue instanceof Collection collection) {
            if (collection.isEmpty()) {
                return;
            }
            queryWrapper.in(
                fieldName,
                collection
            );
            return;
        }
        throw new IllegalArgumentException("IN 查询字段必须为集合！");
    }

    @Override
    public QueryType provideQueryType() {
        return QueryType.IN;
    }
}
