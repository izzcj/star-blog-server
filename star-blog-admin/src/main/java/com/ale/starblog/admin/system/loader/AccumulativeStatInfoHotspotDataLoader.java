package com.ale.starblog.admin.system.loader;

import com.ale.starblog.admin.blog.service.IArticleService;
import com.ale.starblog.admin.blog.service.ICommentService;
import com.ale.starblog.admin.common.cache.HotspotDataLoader;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.domain.entity.DailyStatInfo;
import com.ale.starblog.admin.system.domain.pojo.stat_info.accumulative.AccumulativeStatInfoVO;
import com.ale.starblog.admin.system.service.IDailyStatInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 累计统计信息热点数据加载器
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 15:36
 */
@Component
@RequiredArgsConstructor
public class AccumulativeStatInfoHotspotDataLoader implements HotspotDataLoader<AccumulativeStatInfoVO> {

    /**
     * 每日统计信息服务
     */
    private final IDailyStatInfoService dailyStatInfoService;

    /**
     * 文章服务
     */
    private final IArticleService articleService;

    /**
     * 评论服务
     */
    private final ICommentService commentService;

    @Override
    public AccumulativeStatInfoVO load() {
        int accumulativeViewCount = 0;
        int accumulativeRegisterCount = 0;
        List<DailyStatInfo> statInfoList = this.dailyStatInfoService.lambdaQuery()
            .select(DailyStatInfo::getViewCount, DailyStatInfo::getRegisterCount)
            .list();
        for (DailyStatInfo statInfo : statInfoList) {
            accumulativeViewCount += statInfo.getViewCount();
            accumulativeRegisterCount += statInfo.getRegisterCount();
        }
        long articleCount = this.articleService.count();
        long commentCount = this.commentService.count();
        return AccumulativeStatInfoVO.builder()
            .viewCount(accumulativeViewCount)
            .registerCount(accumulativeRegisterCount)
            .articleCount((int) articleCount)
            .commentCount((int) commentCount)
            .build();
    }

    @Override
    public String provide() {
        return StatInfoConstants.ACCUMULATIVE_STAT_INFO_CACHE_KEY;
    }
}
