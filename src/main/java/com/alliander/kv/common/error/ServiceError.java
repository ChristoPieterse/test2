package com.alliander.kv.common.error;

import com.alliander.kv.common.result.ResultError;

public interface ServiceError {

    String getCode();

    String getMessage();

    ResultError toResultError();
}
