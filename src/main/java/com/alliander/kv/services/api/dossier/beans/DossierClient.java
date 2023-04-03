package com.alliander.kv.services.api.dossier.beans;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
public interface DossierClient {

    @Nullable
    String getName();
}
