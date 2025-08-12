package com.ale.starblog.framework.workflow.db;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * postgresql sql片段构建器
 *
 * @author Ale
 * @version 1.0.0 2025/8/1 14:22
 */
@Component
public class PostgresqlSqlStatementsBuilder implements SqlStatementsBuilder {

    @Override
    public List<String> buildSqlStatements(String sql) {
        String[] lines = sql.split("\n");
        List<String> statements = Lists.newArrayList();
        StringBuilder current = new StringBuilder();
        boolean insideDollarBlock = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("DO $$ BEGIN")) {
                insideDollarBlock = true;
            }
            current.append(line).append("\n");
            if (insideDollarBlock && trimmed.endsWith("$$;")) {
                statements.add(current.toString().trim());
                insideDollarBlock = false;
            }
        }
        return statements;
    }

    @Override
    public boolean supports(String s) {
        return "postgresql".equals(s);
    }
}
