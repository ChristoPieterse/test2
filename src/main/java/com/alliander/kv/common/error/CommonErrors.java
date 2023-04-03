package com.alliander.kv.common.error;

import com.alliander.kv.common.result.ResultError;

public enum CommonErrors {

    ERR_001(1, "An error has occurred.");

    private final String code;
    private final String message;

    CommonErrors(final Integer code, final String message) {
        this.code = String.format("ERR_%03d", code);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ResultError toResultError() {
        return ResultError.create(code, message);
    }
}
