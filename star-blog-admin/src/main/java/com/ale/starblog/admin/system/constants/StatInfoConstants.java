package com.ale.starblog.admin.system.constants;

import lombok.NoArgsConstructor;

/**
 * 统计信息常量
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 10:05
 */
@NoArgsConstructor
public class StatInfoConstants {

    /**
     * 每日统计信息缓存键
     */
    public static final String DAILY_STAT_INFO_CACHE_KEY = "dailyStatInfo";

    /**
     * 累计统计信息缓存键
     */
    public static final String ACCUMULATIVE_STAT_INFO_CACHE_KEY = "accumulativeStatInfo";

    /**
     * 统计信息类型-访问量
     */
    public static final String STAT_INFO_TYPE_VIEW_COUNT = "viewCount";

    /**
     * 统计信息类型-注册用户量
     */
    public static final String STAT_INFO_TYPE_REGISTER_COUNT = "registerCount";

    /**
     * 统计信息类型-活跃用户量
     */
    public static final String STAT_INFO_TYPE_ACTIVE_COUNT = "activeCount";

    /**
     * 统计信息类型-文章数量
     */
    public static final String STAT_INFO_TYPE_ARTICLE_COUNT = "articleCount";

    /**
     * 统计信息类型-评论数量
     */
    public static final String STAT_INFO_TYPE_COMMENT_COUNT = "commentCount";

}
