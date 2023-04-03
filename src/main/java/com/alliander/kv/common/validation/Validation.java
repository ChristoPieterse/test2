package com.alliander.kv.common.validation;

import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;

public interface Validation<R> {

    Result<R, ResultError> validate(R request);
}
