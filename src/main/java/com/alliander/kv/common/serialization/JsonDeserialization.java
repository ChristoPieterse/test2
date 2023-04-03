package com.alliander.kv.common.serialization;

import com.alliander.kv.common.exception.AllianderException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.StreamSupport;

public class JsonDeserialization {

    final JsonNode jsonNode;

    public JsonDeserialization(final String request) {
        try {
            this.jsonNode = new ObjectMapper().readTree(request);
        } catch (JsonProcessingException e) {
            throw new AllianderException(e.getMessage());
        }
    }

    private JsonDeserialization(final JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public JsonDeserialization string(final String fieldName, final Consumer<String> setter) {
        Optional.of(jsonNode.get(fieldName))
                .filter(value -> jsonNode.hasNonNull(fieldName))
                .ifPresent(value -> setter.accept(jsonNode.get(fieldName).asText()));
        return this;
    }

    public JsonDeserialization integer(final String fieldName, final IntConsumer setter) {
        Optional.of(jsonNode.get(fieldName))
                .filter(value -> jsonNode.hasNonNull(fieldName))
                .ifPresent(value -> setter.accept(Integer.parseInt(value.asText())));
        return this;
    }

    public JsonDeserialization bool(final String fieldName, final Consumer<Boolean> setter) {
        Optional.of(jsonNode.get(fieldName))
                .filter(value -> jsonNode.hasNonNull(fieldName))
                .ifPresent(value -> setter.accept(Boolean.parseBoolean(value.asText())));
        return this;
    }

    public <R> JsonDeserialization object(final String fieldName,
                                          final Consumer<R> setter,
                                          final Function<JsonDeserialization, R> function) {
        Optional.of(jsonNode.get(fieldName))
                .filter(value -> jsonNode.hasNonNull(fieldName))
                .ifPresent(value -> {
                    final R object = function.apply(new JsonDeserialization(value));
                    setter.accept(object);
                });
        return this;
    }

    public <R> JsonDeserialization list(final String fieldName,
                                        final Consumer<Collection<R>> setter,
                                        final Function<JsonDeserialization, R> function) {
        Optional.of(jsonNode.get(fieldName))
                .filter(value -> jsonNode.hasNonNull(fieldName))
                .ifPresent(values -> {
                    final Collection<R> objects = StreamSupport.stream(values.spliterator(), false)
                            .map(value -> function.apply(new JsonDeserialization(value))).toList();
                    setter.accept(objects);
                });
        return this;
    }
}
