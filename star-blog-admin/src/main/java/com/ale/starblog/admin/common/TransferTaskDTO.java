package com.ale.starblog.admin.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ale
 * @version 1.0.0 2025/7/1 15:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferTaskDTO extends CompleteTaskDTO {

    /**
     * 代理人ID
     */
    private String agentId;

}
