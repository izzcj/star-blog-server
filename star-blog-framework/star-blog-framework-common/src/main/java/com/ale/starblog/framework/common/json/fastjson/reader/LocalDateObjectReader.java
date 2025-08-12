package com.ale.starblog.framework.common.json.fastjson.reader;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * 日期反序列化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/4
 */
@Component
public class LocalDateObjectReader implements ObjectReader<LocalDate> {

    /**
     * 反序列化
     *
     * @param jsonReader JSON读取器
     * @param fieldType 字段类型
     * @param fieldName 字段名称
     * @param features 特性
     * @return 日期对象
     * @throws JSONException If a suitable ObjectReader is not found
     */
    @Override
    public LocalDate readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        var value = jsonReader.readString();
        if (StrUtil.isEmpty(value)) {
            return null;
        }

        return LocalDateTimeUtil.parseDate(value, DatePattern.NORM_MONTH_PATTERN);
    }
}
