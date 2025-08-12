package com.ale.starblog.framework.workflow.parser;

import com.ale.starblog.framework.workflow.model.AssigneeConfig;
import com.ale.starblog.framework.workflow.model.TaskAssignee;

import java.util.List;

/**
 * 任务受理人解析器
 *
 * @author Ale
 * @version 1.0.0 2025/6/11 11:00
 */
public interface TaskAssigneeParser {

    /**
     * 解析任务受理人
     *
     * @param assigneeConfig 任务受理人配置
     * @return 任务受理人
     */
    List<TaskAssignee> parser(List<AssigneeConfig> assigneeConfig);

}
