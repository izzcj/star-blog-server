package com.ale.starblog.admin.system.domain.pojo.stat_info.accumulative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 累计统计信息VO
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:37
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccumulativeStatInfoVO {

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 注册用户量
     */
    private Integer registerCount;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

}
