package com.ale.starblog.admin.system.listener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 每日统计信息改变事件
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 10:03
 */
@Getter
public class DailyStatInfoChangeEvent extends ApplicationEvent {

    /**
     * 改变类型
     */
    private final String type;

    /**
     * 改变数量
     */
    private final int count;

    public DailyStatInfoChangeEvent(Object source, String type, int count) {
        super(source);
        this.type = type;
        this.count = count;
    }
}
