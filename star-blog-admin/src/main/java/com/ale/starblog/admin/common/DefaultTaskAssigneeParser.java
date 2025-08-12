package com.ale.starblog.admin.common;

import cn.hutool.core.collection.CollectionUtil;
import com.ale.starblog.framework.workflow.model.AssigneeConfig;
import com.ale.starblog.framework.workflow.model.TaskAssignee;
import com.ale.starblog.framework.workflow.parser.TaskAssigneeParser;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Ale
 * @version 1.0.0 2025/6/30 14:31
 */
@Component
public class DefaultTaskAssigneeParser implements TaskAssigneeParser {

    @Override
    public List<TaskAssignee> parser(List<AssigneeConfig> assigneeConfig) {
        if (CollectionUtil.isEmpty(assigneeConfig)) {
            return Collections.emptyList();
        }
        List<TaskAssignee> result = Lists.newArrayList();
        for (AssigneeConfig config : assigneeConfig) {
            Object configValue = config.getValue();
            if (configValue == null) {
                continue;
            }
            if (Objects.equals(config.getType(), "department")
                || Objects.equals(config.getType(), "user")
                || Objects.equals(config.getType(), "title")
                || Objects.equals(config.getType(), "role")) {
                if (configValue instanceof String stringValue) {
                    result.add(
                        TaskAssignee.builder()
                            .id(stringValue)
                            .weight(1)
                            .build()
                    );
                    continue;
                }
                if (configValue instanceof Collection collection) {
                    for (Object value : collection) {
                        result.add(
                            TaskAssignee.builder()
                                .id(value.toString())
                                .weight(1)
                                .build()
                        );
                    }
                }
            }
        }
        return result;
    }
}
