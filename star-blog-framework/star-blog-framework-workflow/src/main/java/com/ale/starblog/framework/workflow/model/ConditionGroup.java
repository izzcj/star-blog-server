package com.ale.starblog.framework.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 条件组
 *
 * @author Ale
 * @version 1.0.0 2025/6/11 14:30
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionGroup {

    /**
     * 条件
     */
    private List<Condition> conditions;
}
