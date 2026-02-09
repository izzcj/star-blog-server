package com.ale.starblog.admin.blog.listener;

import com.ale.starblog.admin.blog.domain.pojo.activity.ActivityBO;
import com.ale.starblog.admin.blog.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 动态发布事件监听器
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 15:34
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityPublishedEventListener {

    /**
     * 动态服务
     */
    private final ActivityService activityService;

    /**
     * 动态发布事件处理
     *
     * @param event 动态发布事件
     */
    @TransactionalEventListener(ActivityPublishedEvent.class)
    public void onActivityPublishedEvent(ActivityPublishedEvent event) {
        try {
            ActivityBO activityBO = event.getActivityBO();
            this.activityService.save(activityBO.toEntity());
            log.info("动态[{}]发布成功!", activityBO.getContent());
        } catch (Exception e) {
            log.error("动态发布事件处理失败", e);
        }
    }

}
