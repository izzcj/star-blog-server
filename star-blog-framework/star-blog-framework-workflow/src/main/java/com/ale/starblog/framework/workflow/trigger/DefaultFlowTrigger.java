package com.ale.starblog.framework.workflow.trigger;

import com.ale.starblog.framework.common.support.Comment;
import com.ale.starblog.framework.workflow.entity.FlowInstance;
import java.util.Map;

/**
 * 默认流程触发器
 *
 * @author Ale
 * @version 1.0.0 2025/7/16 14:25
 */
@Comment("默认触发器")
public class DefaultFlowTrigger implements FlowTrigger {

    @Override
    public boolean execute(FlowInstance instance, Map<String, Object> param) {
        // 不做任何处理
        return true;
    }
}
