package com.ale.starblog.framework.workflow.executor;

import com.ale.starblog.framework.workflow.model.Condition;

/**
 * 表达式构建器
 *
 * @author Ale
 * @version 1.0.0 2025/6/20 11:06
 */
public interface ExpressionBuilder {

    /**
     * 构建表达式
     *
     * @param condition 条件
     * @return 表达式
     */
    String build(Condition condition);

}
