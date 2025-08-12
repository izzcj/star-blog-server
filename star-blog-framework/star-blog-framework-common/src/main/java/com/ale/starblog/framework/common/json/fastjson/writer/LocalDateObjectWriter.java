package com.ale.starblog.framework.common.json.fastjson.writer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * 日期序列化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/4
 */
@Component
public class LocalDateObjectWriter implements ObjectWriter<LocalDate> {

    /**
     * 序列化
     *
     * @param jsonWriter JSON写入器
     * @param object 值
     * @param fieldName 字段名称
     * @param fieldType 字段类型
     * @param features 特性
     */
    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        jsonWriter.writeString(
            LocalDateTimeUtil.format(
                (LocalDate) object,
                DatePattern.NORM_MONTH_PATTERN
            )
        );
    }
}
