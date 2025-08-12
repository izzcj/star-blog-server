package com.ale.starblog.framework.core.translation;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 通用翻译映射数据
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:05
 */
@Data
@Builder
public class GenericTranslationMappingData {

    /**
     * 翻译类型
     */
    private String type;

    /**
     * 翻译参数
     */
    private Map<String, Object> params;

    /**
     * 翻译映射数据
     */
    private Map<String, String> mappingData;
}
