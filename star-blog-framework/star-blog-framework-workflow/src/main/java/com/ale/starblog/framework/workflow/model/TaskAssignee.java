package com.ale.starblog.framework.workflow.model;

import lombok.Builder;
import lombok.Data;


/**
 * 任务受理人
 *
 * @author Ale
 * @version 1.0.0 2025/6/12 9:01
 */
@Data
@Builder
public class TaskAssignee {

    /**
     * 受理人id
     */
    private String id;

    /**
     * 权重
     */
    private Integer weight;
}
