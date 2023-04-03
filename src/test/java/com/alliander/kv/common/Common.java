package com.alliander.kv.common;

import com.alliander.kv.common.error.ServiceError;
import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Common {

    public static <R> Arguments addRequest(final R request, final String expectedErrorCode) {
        return Arguments.of(request, expectedErrorCode);
    }

    public static <R> void assertSuccess(final Result<R, ResultError> result) {
        assertError(result, null);
    }

    public static <R> void assertError(final Result<R, ResultError> error, ServiceError expectedError) {
        Optional.ofNullable(error.getError())
                .ifPresentOrElse(resultError ->
                        assertEquals(resultError.getCodes(), expectedError.getCode()), () -> assertNull(expectedError));
    }
}
