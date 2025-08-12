package com.ale.starblog.framework.workflow.db;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mysql sql片段构建器
 *
 * @author Ale
 * @version 1.0.0 2025/8/1 14:16
 */
@Component
public class MysqlSqlStatementsBuilder implements SqlStatementsBuilder {

    @Override
    public List<String> buildSqlStatements(String sql) {
        String[] split = sql.split(";");
        List<String> sqlStatements = Lists.newArrayList();
        for (String sqlStatement : split) {
            String trimmed = sqlStatement.trim();
            if (!trimmed.isEmpty()) {
                sqlStatements.add(trimmed);
            }
        }
        return sqlStatements;
    }

    @Override
    public boolean supports(String s) {
        return "mysql".equals(s);
    }
}
