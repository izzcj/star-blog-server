package com.ale.starblog.framework.workflow.parser;

import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.model.InstanceModel;

/**
 * 流程实例模型解析器
 *
 * @author Ale
 * @version 1.0.0 2025/6/11 17:51
 */
public interface InstanceModelParser {

    /**
     * 解析流程实例模型
     *
     * @param flowInstance 流程实例
     * @return 流程定义模型
     */
    InstanceModel parse(FlowInstance flowInstance);

}
