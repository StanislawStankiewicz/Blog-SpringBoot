package com.blog.blogspringboot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Log log = LogFactory.getLog(JsonUtil.class);

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e);
            return null;
        }
    }
}
