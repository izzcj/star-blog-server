package com.ale.starblog.admin.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ale
 * @version 1.0.0 2025/6/30 11:08
 */
@Data
public class StartInstanceDTO implements Serializable {

    /**
     * 流程定义ID
     */
    private String definitionId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 流程变量
     */
    private Map<String, Object> variable;
}
