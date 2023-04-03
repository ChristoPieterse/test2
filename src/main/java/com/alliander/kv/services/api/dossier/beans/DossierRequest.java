package com.alliander.kv.services.api.dossier.beans;


import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value.Immutable
public interface DossierRequest {

    @Nullable
    String getReferenceNumber();

    @Nullable
    Integer getIntegerExample();

    @Nullable
    Boolean getBooleanExample();

    @Nullable
    DossierClient getClient();

    @Nullable
    List<DossierProduct> getProducts();
}
