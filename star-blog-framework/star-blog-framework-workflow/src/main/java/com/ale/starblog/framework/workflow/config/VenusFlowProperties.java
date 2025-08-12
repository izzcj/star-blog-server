package com.ale.starblog.framework.workflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Venus流程配置
 *
 * @author Ale
 * @version 1.0.0 2025/7/8 9:27
 */
@Data
@ConfigurationProperties(prefix = "venus.workflow")
public class VenusFlowProperties {

    /**
     * 是否生成逻辑节点任务
     *
     * @see com.ale.starblog.framework.workflow.model.node.LogicNode
     */
    private boolean generateLogicTask;

    /**
     * 是否生成数据库表
     */
    private boolean generateDatabaseTable;

}
