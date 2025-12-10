package com.ale.starblog.admin.blog.listener;

import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 动态发布事件
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:31
 */
@Getter
public class ActivityPublishedEvent extends ApplicationEvent {

    /**
     * 动态
     */
    private final ActivityBO activityBO;

    public ActivityPublishedEvent(Object source, ActivityBO activityBO) {
        super(source);
        this.activityBO = activityBO;
    }
}
