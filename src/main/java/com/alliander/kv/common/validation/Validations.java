package com.alliander.kv.common.validation;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Validations {

    private Validations() {
        throw new IllegalStateException("Utility class");
    }

    //Objects
    public static final Predicate<?> isNotNull = Objects::isNull;

    //Strings
    public static final Predicate<String> stringIsNotBlank = v -> !StringUtils.hasText(v);
    public static final Predicate<String> stringIsAlphaNumeric = v -> v != null && !v.matches("^[a-zA-Z0-9]*$");

    //Collections
    public static final Predicate<List<?>> collectionIsNotEmpty = v -> v == null || v.isEmpty();
}
