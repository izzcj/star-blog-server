package com.ale.starblog.admin.system.domain.pojo.stat_info.daily;

import com.ale.starblog.admin.system.domain.entity.DailyStatInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 每日统计信息BO
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 9:22
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStatInfoBO {

    /**
     * ID
     */
    private Long id;

    /**
     * 统计日期
     */
    private LocalDate statDate;

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 注册用户量
     */
    private Integer registerCount;

    /**
     * 活跃用户量
     */
    private Integer activeCount;

    /**
     * 实例化
     *
     * @param statDate 统计日期
     * @return 实例
     */
    public static DailyStatInfoBO newInstance(LocalDate statDate) {
        return DailyStatInfoBO.builder()
            .id(null)
            .statDate(statDate)
            .viewCount(0)
            .registerCount(0)
            .activeCount(0)
            .build();
    }

    /**
     * 转换为实体
     *
     * @return 实体
     */
    public DailyStatInfo toEntity() {
        return DailyStatInfo.builder()
            .id(this.id)
            .statDate(this.statDate)
            .viewCount(this.viewCount)
            .registerCount(this.registerCount)
            .activeCount(this.activeCount)
            .build();
    }

    /**
     * 将实体转换为BO
     *
     * @param entity 实体
     * @return BO
     */
    public static DailyStatInfoBO convertFromEntity(DailyStatInfo entity) {
        return DailyStatInfoBO.builder()
            .id(entity.getId())
            .statDate(entity.getStatDate())
            .viewCount(entity.getViewCount())
            .registerCount(entity.getRegisterCount())
            .activeCount(entity.getActiveCount())
            .build();
    }

}
