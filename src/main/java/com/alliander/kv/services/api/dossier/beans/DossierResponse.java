package com.alliander.kv.services.api.dossier.beans;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value.Immutable
public interface DossierResponse {

    @Nullable
    String getReferenceNumber();

    @Nullable
    DossierClient getDossierClient();

    @Nullable
    List<DossierProduct> getProducts();
}
