package com.fb.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author: pangminpeng
 * @create: 2020-07-18 17:56
 */
public class LocalDateConvert extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
