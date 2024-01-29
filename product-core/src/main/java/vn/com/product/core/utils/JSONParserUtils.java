package vn.com.product.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@UtilityClass
public class JSONParserUtils {

    private static final ObjectMapper objectMapper;
    private static final String PARSE_OBJECT_ERROR_MESSAGE = "Can't parse object from json string: {} - {}";
    private static final String WRITE_OBJECT_ERROR_MESSAGE = "Can't write value as string from object: {}";

    static {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static String writeValueAsString(Object jsonObject) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (Exception ex) {
            log.error(WRITE_OBJECT_ERROR_MESSAGE, jsonObject);
            return null;
        }
    }

    public static String prettyPrintString(String jsonString) {
        try {
            return objectMapper.readTree(jsonString).toPrettyString();
        } catch (JsonProcessingException e) {
            return jsonString;
        }
    }

    public static <C extends Collection<?>, T> Collection<T> parseToCollection(String jsonString, Class<C> collectionClazz, Class<T> targetClazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(collectionClazz, targetClazz);
            return objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, targetClazz.getName());
            return Collections.emptyList();
        }
    }

    public static <T> Collection<T> parseToList(String jsonString, Class<T> targetClazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, targetClazz);
            return objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, targetClazz.getName());
            return Collections.emptyList();
        }
    }

    public static <T> T parseToObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException ex) {
            log.error(PARSE_OBJECT_ERROR_MESSAGE, jsonString, clazz);
            return null;
        }
    }
}
