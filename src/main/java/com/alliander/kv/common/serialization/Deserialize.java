package com.alliander.kv.common.serialization;

public interface Deserialize<R> {

    R deserialize(String request);
}
