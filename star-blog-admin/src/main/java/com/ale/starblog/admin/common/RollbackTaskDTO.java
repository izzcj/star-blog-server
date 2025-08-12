package com.ale.starblog.admin.common;

import com.ale.starblog.framework.workflow.support.FinishTaskParam;
import lombok.Data;

/**
 * 回退任务参数
 *
 * @author Ale
 * @version 1.0.0 2025/7/11 15:51
 */
@Data
public class RollbackTaskDTO {

    /**
     * 回退节点ID
     */
    String rollbackNodeId;

    /**
     * 是否跳回当前节点
     */
    Boolean jumpCurrentNode;

    /**
     * 回退任务参数
     */
    FinishTaskParam rollbackTaskParam;

}
