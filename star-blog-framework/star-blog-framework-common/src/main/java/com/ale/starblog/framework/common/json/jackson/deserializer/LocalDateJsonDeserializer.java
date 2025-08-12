package com.ale.starblog.framework.common.json.jackson.deserializer;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.time.format.DateTimeFormatter;

/**
 * 日期反序列化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/4
 */
@JsonComponent
public class LocalDateJsonDeserializer extends LocalDateDeserializer {
    public LocalDateJsonDeserializer() {
        super(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
    }
}
