package com.ale.starblog.framework.core.translation;

import com.ale.starblog.framework.common.support.Supportable;
import java.util.Map;

/**
 * 通用翻译器
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 14:38
 */
public interface GenericTranslator extends Supportable<String> {

    /**
     * 翻译
     *
     * @param type   数据类型
     * @param params 参数
     * @param value  值
     * @return 翻译后的值
     */
    String translate(String type, Map<String, Object> params, String value);

}
