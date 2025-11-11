package com.ale.starblog.framework.core.query.condition;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.core.constants.DataBaseConstants;
import com.ale.starblog.framework.core.query.QueryParameter;
import com.ale.starblog.framework.core.query.QueryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 排序
 * 支持多字段排序
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/12
 */
@Component
public class SortCondition implements QueryCondition {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void build(String fieldName, Object fieldValue, QueryWrapper<?> queryWrapper, QueryParameter[] parameters) {
        String order = DataBaseConstants.ORDER_ASC;
        for (QueryParameter parameter : parameters) {
            if ("order".equals(parameter.name())) {
                order = parameter.value();
            }
        }
        List<String> sortFields = new ArrayList<>();
        if (fieldValue instanceof String stringValue) {
            sortFields.add(stringValue);
        } else if (fieldValue instanceof Collection collection) {
            sortFields.addAll(collection);
        } else {
            throw new IllegalArgumentException("排序字段必须为String或Collection");
        }
        queryWrapper.orderBy(
            CollectionUtil.isNotEmpty(sortFields),
            DataBaseConstants.ORDER_ASC.equals(order),
            sortFields
        );
    }

    @Override
    public QueryType provideQueryType() {
        return QueryType.SORT;
    }
}
