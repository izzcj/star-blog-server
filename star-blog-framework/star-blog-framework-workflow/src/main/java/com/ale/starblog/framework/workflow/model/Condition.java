package com.ale.starblog.framework.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 条件
 *
 * @author Ale
 * @version 1.0.0 2025/6/9 17:30
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condition {

    /**
     * 字段
     */
    private String field;

    /**
     * 操作符
     */
    private String operator;

    /**
     * 值
     */
    private Object value;

}
