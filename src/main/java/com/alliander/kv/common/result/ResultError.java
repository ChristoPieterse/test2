package com.alliander.kv.common.result;

import com.alliander.kv.common.error.ServiceError;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultError {

    private final Map<String, String> map = new HashMap<>();

    private ResultError() {

    }

    private ResultError(final String code, final String message) {
        map.put(code, message);
    }

    public static ResultError create() {
        return new ResultError();
    }

    public static ResultError create(final String code, final String message) {
        return new ResultError(code, message);
    }

    public Map<String, String> getErrors() {
        return map;
    }

    public String getCodes() {
        return String.join(",", map.keySet());
    }

    public boolean hasErrors() {
        return !map.isEmpty();
    }

    public void add(ServiceError error) {
        map.put(error.getCode(), error.getMessage());
    }
}
