package com.ale.starblog.framework.core.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * BO基类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseBO {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
