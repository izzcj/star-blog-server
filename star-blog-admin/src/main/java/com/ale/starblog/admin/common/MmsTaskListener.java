package com.ale.starblog.admin.common;

import com.ale.starblog.framework.workflow.entity.FlowTask;
import com.ale.starblog.framework.workflow.entity.FlowTaskActor;
import com.ale.starblog.framework.workflow.hook.TaskEventType;
import com.ale.starblog.framework.workflow.hook.TaskListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ale
 * @version 1.0.0 2025/7/1 15:53
 */
@Slf4j
@Component
public class MmsTaskListener implements TaskListener {

    @Override
    public boolean notify(TaskEventType eventType, FlowTask flowTask, List<FlowTaskActor> taskActors) {
        for (FlowTaskActor taskActor : taskActors) {
            log.info("完成任务消息通知，受理人[{}]", taskActor.getActorId());
        }
        return true;
    }

    @Override
    public boolean supports(TaskEventType taskEventType) {
        return TaskEventType.AFTER_COMPLETE.match(taskEventType);
    }
}
