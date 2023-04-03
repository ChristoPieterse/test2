package com.alliander.kv.services.impl.dossier;

import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import com.alliander.kv.common.validation.Validation;
import com.alliander.kv.common.validation.ValidationContext;
import com.alliander.kv.services.api.dossier.beans.DossierRequest;
import com.alliander.kv.services.api.dossier.beans.ImmutableDossierRequest;
import org.springframework.stereotype.Component;

import static com.alliander.kv.common.validation.Validations.*;
import static com.alliander.kv.services.impl.dossier.DossierErrors.*;

@Component
class DossierValidation implements Validation<DossierRequest> {

    @Override
    public Result<DossierRequest, ResultError> validate(final DossierRequest request) {

        final ValidationContext ctx = new ValidationContext();

        final DossierRequest preProcessedRequest = preProcessRequest(request);
        performValidations(preProcessedRequest, ctx);

        return ctx.validated(preProcessedRequest);
    }

    private DossierRequest preProcessRequest(final DossierRequest request) {

        return ImmutableDossierRequest.builder()
                .from(request)
                .build();
    }

    private void performValidations(final DossierRequest request, final ValidationContext ctx) {

        ctx.validate(request.getReferenceNumber())
                .with(stringIsNotBlank, DOS_001)
                .with(stringIsAlphaNumeric, DOS_004);

        ctx.validate(request.getIntegerExample())
                .with(isNotNull, DOS_005);

        ctx.validate(request.getProducts())
                .with(collectionIsNotEmpty, DOS_003);
    }
}