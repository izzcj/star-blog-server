package com.ale.starblog.framework.workflow.support;

import cn.hutool.core.util.IdUtil;

/**
 * 雪花id生成器
 *
 * @author Ale
 * @version 1.0.0 2025/6/26 17:36
 */
public class SnowflakeIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        return IdUtil.getSnowflakeNextIdStr();
    }
}
