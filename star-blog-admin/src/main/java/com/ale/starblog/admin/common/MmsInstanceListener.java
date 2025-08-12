package com.ale.starblog.admin.common;

import com.ale.starblog.framework.workflow.entity.FlowInstance;
import com.ale.starblog.framework.workflow.hook.InstanceEventType;
import com.ale.starblog.framework.workflow.hook.InstanceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 流程实例监听器
 *
 * @author Ale
 * @version 1.0.0 2025/7/14 14:01
 */
@Slf4j
@Component
public class MmsInstanceListener implements InstanceListener {

    @Override
    public boolean notify(InstanceEventType eventType, FlowInstance flowInstance) {
        log.info("流程实例消息通知，事件类型：{}", eventType);
        return true;
    }

    @Override
    public boolean supports(InstanceEventType eventType) {
        return InstanceEventType.AFTER_UPDATE.match(eventType);
    }
}
