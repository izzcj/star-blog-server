package com.ale.starblog.framework.workflow.db;

import com.ale.starblog.framework.common.support.Supportable;

import java.util.List;

/**
 * sql片段构建器
 *
 * @author Ale
 * @version 1.0.0 2025/8/1 14:13
 */
public interface SqlStatementsBuilder extends Supportable<String> {

    /**
     * 构建sql片段
     *
     * @param sql    sql
     * @return sql片段
     */
    List<String> buildSqlStatements(String sql);

}
