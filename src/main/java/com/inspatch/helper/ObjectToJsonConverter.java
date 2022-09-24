package com.inspatch.helper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import com.inspatch.annotation.JsonElement;
import com.inspatch.annotation.JsonList;
import com.inspatch.annotation.JsonSerializable;

public class ObjectToJsonConverter {
    public String convertToJson(Object object) throws JsonSerializationException {
        try {

            checkIfSerializable(object);
            return getJsonString(object);

        } catch (Exception e) {
            throw new JsonSerializationException(e.getMessage());
        }
    }

    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException("Can't serialize a null object");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new JsonSerializationException(
                    "The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable");
        }
    }

    /**
     * This method require that the feilds feeded must be sorted
     * as json elements, and json lists content in order.
     * 
     * @param object
     * @return the json string
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private String getJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        LinkedList<String> result = new LinkedList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            StringBuilder buffer = new StringBuilder();
            if (field.isAnnotationPresent(JsonList.class) && !getListingMode(field)) {
                result.add(buffer.append("\"").append(getKey(field)).append("\": [")
                        .append(getStringList(field, object)).append("]").toString());
            } else if (field.isAnnotationPresent(JsonList.class) && getListingMode(field)) {
                result.add(buffer.append("\"").append(getKey(field)).append("\": [")
                        .append(getObjectList(field, object)).append("]").toString());
            } else if (field.isAnnotationPresent(JsonElement.class)) {
                result.add(buffer.append("\"").append(getKey(field)).append("\": \"")
                        .append(String.valueOf(field.get(object))).append("\"").toString());
            }
        }
        return "{" + result.stream().collect(Collectors.joining(",")) + "}";
    }

    private String getStringList(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        return Arrays.asList((String[]) field.get(object)).stream().map(v -> "\"" + v + "\"")
                .collect(Collectors.joining(","));
    }

    private String getObjectList(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        return Arrays.asList((Object[]) field.get(object)).stream().map(v -> convertToJson(v))
                .collect(Collectors.joining(","));
    }

    private String getKey(Field field) {
        String value = "";
        if (field.isAnnotationPresent(JsonList.class)) {
            value = field.getAnnotation(JsonList.class)
                    .key();
        } else if (field.isAnnotationPresent(JsonElement.class)) {
            value = field.getAnnotation(JsonElement.class)
                    .key();
        }
        return value.isEmpty() ? field.getName() : value;
    }

    private boolean getListingMode(Field field) {
        boolean value = field.getAnnotation(JsonList.class)
                .ofObjects();
        return value;
    }
}
