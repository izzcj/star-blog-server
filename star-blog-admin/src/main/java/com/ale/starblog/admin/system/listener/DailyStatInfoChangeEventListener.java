package com.ale.starblog.admin.system.listener;

import com.ale.starblog.admin.common.cache.CacheableHotspotDataManager;
import com.ale.starblog.admin.common.support.StatInfoSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 每日统计信息改变事件监听器
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 14:46
 */
@Slf4j
@Component
public class DailyStatInfoChangeEventListener extends StatInfoSupport {

    public DailyStatInfoChangeEventListener(CacheableHotspotDataManager cacheableHotspotDataManager) {
        super(cacheableHotspotDataManager);
    }

    /**
     * 每日统计信息改变事件处理
     *
     * @param event 每日统计信息改变事件
     */
    @EventListener(DailyStatInfoChangeEvent.class)
    public void onDailyStatInfoChangeEvent(DailyStatInfoChangeEvent event) {
        try {
            this.updateStatInfo(event.getType(), event.getCount(), true);
        } catch (Exception e) {
            log.error("每日统计信息更新失败", e);
        }
    }

}
