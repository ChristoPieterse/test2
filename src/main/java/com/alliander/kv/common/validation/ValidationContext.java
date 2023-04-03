package com.alliander.kv.common.validation;

import com.alliander.kv.common.error.ServiceError;
import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;

import java.util.function.Predicate;

public class ValidationContext {

    private Object value;
    private final ResultError resultError = ResultError.create();

    public ValidationContext validate(final Object value) {
        this.value = value;
        return this;
    }

    public final <T> ValidationContext with(Predicate<T> predicate, ServiceError error) {
        if (predicate.test((T) value)) {
            resultError.add(error);
        }
        return this;
    }

    public <T> Result<T, ResultError> validated(T validatedRequest) {
        return resultError.hasErrors()
                ? Result.error(resultError)
                : Result.success(validatedRequest);
    }
}
