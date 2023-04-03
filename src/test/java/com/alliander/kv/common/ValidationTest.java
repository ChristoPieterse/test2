package com.alliander.kv.common;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public interface ValidationTest<T> {

    /**
     * Builds request and expected response pairs onto a stream for testing
     *
     * @return a stream containing request and expected response pairs
     */
    Stream<Arguments> buildTestCases();

    /**
     * The primary method executing a validation test case.
     *
     * @param request the request object to be validated
     * @param expectedErrorCode the expected error response, or success response
     */
    void performValidation(final T request, final String expectedErrorCode);
}