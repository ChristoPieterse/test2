package com.alliander.kv.common.serialization;

import org.springframework.http.ResponseEntity;

public interface Serialize<R> {

    ResponseEntity<Object> serialize(R response);
}
