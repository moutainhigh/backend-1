package com.fb.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public final class JsonUtils {

    private static ObjectMapper objectMapper;

    private JsonUtils() {
    }

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("JsonUtils.json2Object  json = " + json, e);
            return null;
        }
    }

    public static <T> T json2Object(String json, TypeReference<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.warn("json = " + json, e);
            return null;
        }

    }

    public static String object2Json(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("JsonUtils.object2Json occured:", e);
            return null;
        }
    }

    public static <T> List<T> json2List(String json, Class<T> elementClasses) {
        return json2List(json, List.class, elementClasses);
    }

    /**
     * @param json            json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    public static <T> List<T> json2List(String json, Class<? extends List> collectionClass, Class<T> elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("JsonUtils.json2List  json={} ", json, e);
            return null;
        }
    }

    /**
     * @param json
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) {
        return json2Map(json, Map.class, keyClass, valueClass);

    }

    /**
     * @param json
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("JsonUtils.json2Map  json={} ", json, e);
            return null;
        }

    }

    public static JsonNode json2JsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error("jsonString{} to json error", json, e);
        }
        return null;
    }

    public static ObjectNode getEmptyObjectNode() {
        return objectMapper.createObjectNode();
    }
}
