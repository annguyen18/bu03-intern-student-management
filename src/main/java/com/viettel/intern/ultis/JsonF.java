package com.viettel.intern.ultis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonF {
    private static final Logger logger = LoggerFactory.getLogger(JsonF.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static String toString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T toObject(Object o, Class<T> clazz) {
        try {
            if (o instanceof String) {
                return mapper.readValue((String) o, clazz);
            }
            String str = mapper.writeValueAsString(o);
            return mapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T toObject(String str, Class<T> clazz) {
        try {
            return mapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    public static <T> T jsonToObject(String str, TypeReference<T> type) {
        try {
            return mapper.readValue(str, type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> List<T> toList(String str, Class<T> clazz) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(str, javaType);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
