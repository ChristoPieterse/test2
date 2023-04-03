package com.alliander.kv.common.result;

import java.util.function.Function;

public class Result<R, E> {

    private final R value;
    private final ResultError error;

    public Result(final R value) {
        this.value = value;
        this.error = null;
    }

    public Result(final ResultError error) {
        this.value = null;
        this.error = error;
    }

    public R getValue() {
        return this.value;
    }

    public ResultError getError() {
        return this.error;
    }

    public static <R> Result<R, ResultError> success() {
        return new Result<>(null);
    }

    public static <R> Result<R, ResultError> success(R value) {
        return new Result<>(value);
    }

    public static <R> Result<R, ResultError> error(ResultError error) {
        return new Result<>(error);
    }

    boolean isError() {
        return error != null;
    }

    public <T> Result<T, ResultError> map(Function<? super R, ? extends Result<? extends T, ? extends ResultError>> mapper) {
        if (isError()) {
            return new Result<>(getError());
        } else {
            return (Result<T, ResultError>) mapper.apply(getValue());
        }
    }
}
