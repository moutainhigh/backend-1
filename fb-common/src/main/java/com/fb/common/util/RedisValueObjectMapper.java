package com.fb.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RedisValueObjectMapper extends ObjectMapper {

    public RedisValueObjectMapper() {
        super();
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }
}
