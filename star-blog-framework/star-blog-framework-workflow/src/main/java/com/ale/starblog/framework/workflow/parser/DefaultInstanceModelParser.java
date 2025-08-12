package com.ale.starblog.framework.workflow.parser;

import cn.hutool.core.util.StrUtil;
import com.ale.starblog.framework.common.utils.JsonUtils;
import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.exception.FlowException;
import com.ale.starblog.framework.workflow.model.InstanceModel;
import com.ale.starblog.framework.workflow.model.node.FlowNode;


/**
 * 默认流程实例模型解析器
 *
 * @author Ale
 * @version 1.0.0 2025/6/11 17:57
 */
public class DefaultInstanceModelParser implements InstanceModelParser {

    @Override
    public InstanceModel parse(FlowInstance flowInstance) {
        if (flowInstance == null) {
            throw new FlowException("解析流程实例模型失败！流程实例为空！");
        }
        if (StrUtil.isBlank(flowInstance.getDesignContent())) {
            throw new FlowException("解析流程实例模型失败！流程设计内容为空！");
        }
        FlowNode flowNode = JsonUtils.fromJson(flowInstance.getDesignContent(), FlowNode.class);
        return InstanceModel.builder()
            .id(flowInstance.getId())
            .businessId(flowInstance.getBusinessId())
            .businessType(flowInstance.getBusinessType())
            .rootNode(JsonUtils.fromJson(flowInstance.getDesignContent(), FlowNode.class))
            .flowInstance(flowInstance)
            .flowNodes(flowNode.flatten())
            .build();
    }
}
