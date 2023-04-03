package com.alliander.kv.services.impl.dossier;

import com.alliander.kv.common.error.ServiceError;
import com.alliander.kv.common.result.ResultError;

public enum DossierErrors implements ServiceError {

    //Validation Errors
    DOS_001(1, "The 'reference number' field must be supplied"),
    DOS_003(3, "At-least one product must be supplied."),
    DOS_004(4, "The 'reference number' may only contain alphanumeric characters"),
    DOS_005(5, "The 'integer example' must be supplied"),

    //Processing Errors
    DOS_002(2, "An error has occurred.");

    private final String code;
    private final String message;

    DossierErrors(final Integer code, final String message) {
        this.code = String.format("DOS_%03d", code);
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ResultError toResultError() {
        return ResultError.create(code, message);
    }
}
