package com.ale.starblog.admin.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ale.starblog.admin.common.cache.CacheableHotspotDataManager;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.domain.pojo.stat_info.accumulative.AccumulativeStatInfoVO;
import com.ale.starblog.admin.system.domain.pojo.stat_info.daily.DailyStatInfoBO;
import com.ale.starblog.admin.system.domain.pojo.stat_info.daily.DailyStatInfoVO;
import com.ale.starblog.framework.common.domain.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统统计接口
 *
 * @author Ale
 * @version 1.0.0 2025/12/8 10:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/stat-info")
public class StatInfoController {

    /**
     * 可缓存热点数据管理器
     */
    private final CacheableHotspotDataManager cacheableHotspotDataManager;

    /**
     * 获取每日统计信息
     *
     * @return 统计信息
     */
    @RequestMapping("/daily")
    public JsonResult<DailyStatInfoVO> fetchDailyStatInfo() {
        DailyStatInfoBO statInfo = this.cacheableHotspotDataManager.get(StatInfoConstants.DAILY_STAT_INFO_CACHE_KEY);
        return JsonResult.success(BeanUtil.copyProperties(statInfo, DailyStatInfoVO.class));
    }

    /**
     * 获取累计统计信息
     *
     * @return 统计信息
     */
    @RequestMapping("/accumulative")
    public JsonResult<AccumulativeStatInfoVO> fetchAccumulativeStatInfo() {
        AccumulativeStatInfoVO statInfo = this.cacheableHotspotDataManager.get(StatInfoConstants.ACCUMULATIVE_STAT_INFO_CACHE_KEY);
        return JsonResult.success(statInfo);
    }

}
