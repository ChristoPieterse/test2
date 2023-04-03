package com.alliander.kv.common.serialization;

import com.alliander.kv.common.exception.AllianderException;
import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static com.alliander.kv.common.error.CommonErrors.ERR_001;

public class JsonSerialization {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode data = mapper.createObjectNode();

    public static <T> ResponseEntity<Object> processResponse(final CompletionStage<Result<T, ResultError>> completionStage,
                                                             final Serialize<T> serialization) {

        try {
            final Result<T, ResultError> result = completionStage.toCompletableFuture().get();

            return Optional.ofNullable(result.getValue()).map(serialization::serialize).orElseGet(()
                    -> new ResponseEntity<>(createErrorBlock(result.getError()), HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(createErrorBlock(ERR_001.toResultError()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static ObjectNode createErrorBlock(final ResultError resultError) {

        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode root = mapper.createObjectNode();

        final ArrayNode errorArrayNode = root.putArray("error");

        resultError.getErrors().keySet().forEach(key -> {
            final ObjectNode error = mapper.createObjectNode();
            error.put("code", key);
            error.put("message", resultError.getErrors().get(key));
            errorArrayNode.add(error);
        });

        return root.set("error", errorArrayNode);
    }

    public ResponseEntity<Object> getResponseEntity() {

        final ObjectNode root = mapper.createObjectNode();
        final ObjectNode dataNode = root.set("data", this.data);

        try {
            final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataNode);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new AllianderException(e.getMessage());
        }
    }

    private ObjectNode getObjectNode() {
        return this.data;
    }

    public JsonSerialization put(final String key, final String value) {
        data.put(key, value);
        return this;
    }

    public <C> JsonSerialization putObject(final String key,
                                           final C value,
                                           final Function<C, JsonSerialization> function) {
        Optional.ofNullable(value)
                .ifPresent(v -> {
                    final JsonSerialization jsonSerialization = function.apply(v);
                    data.set(key, jsonSerialization.getObjectNode());
                });
        return this;
    }

    public <C> JsonSerialization putList(final String key,
                                         final Collection<C> list,
                                         final Function<C, JsonSerialization> function) {
        final ArrayNode arrayNode = data.putArray(key);
        Optional.ofNullable(list).ifPresent(collection -> collection.forEach(v -> {
            final JsonSerialization jsonSerialization = function.apply(v);
            arrayNode.add(jsonSerialization.getObjectNode());
        }));
        return this;
    }
}
