package com.ale.starblog.admin.common.support;

import com.ale.starblog.admin.common.cache.CacheableHotspotDataManager;
import com.ale.starblog.admin.system.constants.StatInfoConstants;
import com.ale.starblog.admin.system.domain.pojo.stat_info.accumulative.AccumulativeStatInfoVO;
import com.ale.starblog.admin.system.domain.pojo.stat_info.daily.DailyStatInfoBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 统计信息支持
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 14:58
 */
@Slf4j
@RequiredArgsConstructor
public abstract class StatInfoSupport {

    /**
     * 可缓存热点数据管理器
     */
    protected final CacheableHotspotDataManager cacheableHotspotDataManager;

    /**
     * 读写锁
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 更新统计信息
     *
     * @param type       统计信息类型
     * @param count      数量
     * @param isIncrease 是否增加
     */
    protected void updateStatInfo(String type, int count, boolean isIncrease) {
        lock.writeLock().lock();
        try {
            DailyStatInfoBO daily = this.cacheableHotspotDataManager.get(StatInfoConstants.DAILY_STAT_INFO_CACHE_KEY);
            AccumulativeStatInfoVO acc = this.cacheableHotspotDataManager.get(StatInfoConstants.ACCUMULATIVE_STAT_INFO_CACHE_KEY);

            if (daily == null || acc == null) {
                return;
            }

            if (isIncrease) {
                increase(type, count, daily, acc);
            } else {
                // 每日统计数据不存在减少的数据，故不处理
                decrease(type, count, acc);
            }

            this.cacheableHotspotDataManager.set(StatInfoConstants.DAILY_STAT_INFO_CACHE_KEY, daily);
            this.cacheableHotspotDataManager.set(StatInfoConstants.ACCUMULATIVE_STAT_INFO_CACHE_KEY, acc);

        } catch (Exception e) {
            log.error("统计信息更新失败", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 增加统计信息
     *
     * @param type  统计信息类型
     * @param count 数量
     * @param daily 每日统计信息
     * @param acc   累计统计信息
     */
    private void increase(String type, int count, DailyStatInfoBO daily, AccumulativeStatInfoVO acc) {
        switch (type) {
            case StatInfoConstants.STAT_INFO_TYPE_VIEW_COUNT:
                daily.setViewCount(daily.getViewCount() + count);
                acc.setViewCount(acc.getViewCount() + count);
                break;
            case StatInfoConstants.STAT_INFO_TYPE_REGISTER_COUNT:
                daily.setRegisterCount(daily.getRegisterCount() + count);
                acc.setRegisterCount(acc.getRegisterCount() + count);
                break;
            case StatInfoConstants.STAT_INFO_TYPE_ACTIVE_COUNT:
                daily.setActiveCount(daily.getActiveCount() + count);
                break;
            case StatInfoConstants.STAT_INFO_TYPE_ARTICLE_COUNT:
                acc.setArticleCount(acc.getArticleCount() + count);
                break;
            case StatInfoConstants.STAT_INFO_TYPE_COMMENT_COUNT:
                acc.setCommentCount(acc.getCommentCount() + count);
                break;
            default:
                break;
        }
    }

    /**
     * 减少统计信息
     *
     * @param type  统计信息类型
     * @param count 数量
     * @param acc   累计统计信息
     */
    private void decrease(String type, int count, AccumulativeStatInfoVO acc) {
        switch (type) {
            case StatInfoConstants.STAT_INFO_TYPE_ARTICLE_COUNT:
                acc.setArticleCount(Math.max(acc.getArticleCount() - count, 0));
                break;
            case StatInfoConstants.STAT_INFO_TYPE_COMMENT_COUNT:
                acc.setCommentCount(Math.max(acc.getCommentCount() - count, 0));
                break;
            default:
                break;
        }
    }

}
