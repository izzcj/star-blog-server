package com.ale.starblog.admin.system.loader;

import com.ale.starblog.admin.common.cache.HotspotDataLoader;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.domain.entity.DailyStatInfo;
import com.ale.starblog.admin.system.domain.pojo.stat_info.daily.DailyStatInfoBO;
import com.ale.starblog.admin.system.service.IDailyStatInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 每日统计信息热点数据加载器
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:51
 */
@Component
@RequiredArgsConstructor
public class DailyStatInfoHotspotDataLoader implements HotspotDataLoader<DailyStatInfoBO> {

    /**
     * 每日统计信息服务
     */
    private final IDailyStatInfoService dailyStatInfoService;

    @Override
    public DailyStatInfoBO load() {
        // 获取当日统计信息
        LocalDate today = LocalDate.now();
        DailyStatInfo dailyStatInfo = this.dailyStatInfoService.lambdaQuery()
            .eq(DailyStatInfo::getStatDate, today)
            .one();
        if (dailyStatInfo == null) {
            return DailyStatInfoBO.newInstance(today);
        }
        return DailyStatInfoBO.convertFromEntity(dailyStatInfo);
    }

    @Override
    public void persistenceHotspotData(DailyStatInfoBO cacheData) {
        this.dailyStatInfoService.saveOrUpdate(cacheData.toEntity());
    }

    @Override
    public String provide() {
        return StatInfoConstants.DAILY_STAT_INFO_CACHE_KEY;
    }
}
