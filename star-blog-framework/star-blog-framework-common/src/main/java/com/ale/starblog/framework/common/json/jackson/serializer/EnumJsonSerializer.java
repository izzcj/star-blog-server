package com.ale.starblog.framework.common.json.jackson.serializer;

import com.ale.starblog.framework.common.constants.VenusConstants;
import com.ale.starblog.framework.common.enumeration.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 枚举序列化器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/25
 */
@JsonComponent
public class EnumJsonSerializer extends StdSerializer<BaseEnum> {

    public EnumJsonSerializer() {
        super(BaseEnum.class);
    }

    @Override
    public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getValue().toString());
        gen.writeStringField(gen.getOutputContext().getCurrentName() + VenusConstants.ENUM_NAME_PROPERTY_SUFFIX, value.getMsg());
    }
}
