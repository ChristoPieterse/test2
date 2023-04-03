package com.alliander.kv.services.serialization.dossier;

import com.alliander.kv.common.serialization.Deserialize;
import com.alliander.kv.common.serialization.JsonDeserialization;
import com.alliander.kv.common.serialization.JsonSerialization;
import com.alliander.kv.common.serialization.Serialize;
import com.alliander.kv.services.api.dossier.beans.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DossierSerialization extends JsonSerialization implements Deserialize<DossierRequest>, Serialize<DossierResponse> {

    @Override
    public DossierRequest deserialize(final String request) {

        final ImmutableDossierRequest.Builder builder = ImmutableDossierRequest.builder();

        new JsonDeserialization(request)
                .string("referenceNumber", builder::referenceNumber)
                .integer("integerExample", builder::integerExample)
                .bool("booleanExample", builder::booleanExample)
                .object("client", builder::client, this::deserializeClient)
                .list("products", builder::products, this::deserializeProducts);

        return builder.build();
    }

    private DossierClient deserializeClient(final JsonDeserialization jsonDeserialization) {

        final ImmutableDossierClient.Builder builder = ImmutableDossierClient.builder();

        jsonDeserialization
                .string("name", builder::name);

        return builder.build();
    }

    private DossierProduct deserializeProducts(final JsonDeserialization jsonDeserialization) {

        final ImmutableDossierProduct.Builder builder = ImmutableDossierProduct.builder();

        jsonDeserialization
                .string("name", builder::name)
                .string("description", builder::description);

        return builder.build();
    }

    @Override
    public ResponseEntity<Object> serialize(final DossierResponse response) {

        return new JsonSerialization()
                .put("referenceNumber", response.getReferenceNumber())
                .putObject("client", response.getDossierClient(), this::serializeClient)
                .putList("products", response.getProducts(), this::serializeProduct)
                .getResponseEntity();
    }

    private JsonSerialization serializeProduct(final DossierProduct product) {

        return new JsonSerialization()
                .put("name", product.getName())
                .put("description", product.getDescription());
    }

    private JsonSerialization serializeClient(final DossierClient client) {

        return new JsonSerialization()
                .put("name", client.getName());
    }
}