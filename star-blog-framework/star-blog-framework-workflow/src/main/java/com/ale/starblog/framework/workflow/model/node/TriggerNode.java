package com.ale.starblog.framework.workflow.model.node;

import com.ale.starblog.framework.workflow.entity.FlowExecution;
import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.enumeration.FlowTaskType;
import com.ale.starblog.framework.workflow.exception.FlowException;
import com.ale.starblog.framework.workflow.support.FlowContext;
import com.ale.starblog.framework.workflow.trigger.DefaultFlowTrigger;
import com.ale.starblog.framework.workflow.trigger.FlowTrigger;
import com.ale.starblog.framework.workflow.trigger.FlowTriggerHolder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 触发器节点
 *
 * @author Ale
 * @version 1.0.0 2025/6/9 17:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TriggerNode extends LogicNode {

    /**
     * 节点类型
     */
    public static final String NODE_TYPE = "trigger";

    /**
     * 执行参数
     */
    @JsonProperty(index = 51)
    private Map<String, Object> param;

    /**
     * 触发器实现类
     */
    @JsonProperty(index = 52)
    private String triggerClass;

    @Override
    public boolean doExecute(FlowInstance instance, FlowExecution lastExecution, Map<String, Object> variable, FlowTask task) {
        FlowTrigger flowTrigger = FlowTriggerHolder.getFlowTrigger(this.triggerClass);
        if (flowTrigger == null) {
            flowTrigger = new DefaultFlowTrigger();
        }
        boolean triggerExecuteResult = flowTrigger.execute(instance, this.param);
        if (!triggerExecuteResult) {
            throw new FlowException("触发器[{}]执行失败！执行节点[{}]", this.triggerClass, this.name);
        }
        FlowExecution triggerExecution = FlowContext.getExecutionService().createExecution(instance, this.id, lastExecution, variable);
        if (task != null) {
            task.setExecutionId(triggerExecution.getId());
            task.setType(FlowTaskType.TRIGGER.getValue());
            FlowContext.getTaskService().addTask(List.of(task), Collections.emptyList(), false);
        }
        return FlowContext.getExecutionService().completeExecution(triggerExecution);
    }
}
