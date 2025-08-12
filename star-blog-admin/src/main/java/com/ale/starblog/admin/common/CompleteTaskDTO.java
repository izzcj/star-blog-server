package com.ale.starblog.admin.common;

import com.ale.starblog.framework.workflow.enumeration.VariableLevel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 完成任务DTO
 *
 * @author Ale
 * @version 1.0.0 2025/7/1 9:25
 */
@Data
public class CompleteTaskDTO {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务处理人ID
     */
    private String assigneeId;

    /**
     * 任务变量
     */
    private Map<String, Object> variable;

    /**
     * 变量级别
     */
    private VariableLevel variableLevel;

    /**
     * 评论
     */
    private String comment;

    /**
     * 附件
     */
    private List<String> attachments;

}
