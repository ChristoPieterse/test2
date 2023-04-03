package com.alliander.kv.services.impl.dossier;

import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import com.alliander.kv.common.validation.Validation;
import com.alliander.kv.services.api.dossier.DossierService;
import com.alliander.kv.services.api.dossier.beans.DossierRequest;
import com.alliander.kv.services.api.dossier.beans.DossierResponse;
import com.alliander.kv.services.api.dossier.beans.ImmutableDossierResponse;
import com.alliander.kv.services.impl.dossier.beans.ImmutableApplicationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.alliander.kv.services.impl.dossier.DossierErrors.DOS_002;

@Component
class DossierImpl implements DossierService {

    private static final Logger logger = LoggerFactory.getLogger(DossierImpl.class);

    private final Validation<DossierRequest> validation;

    @Autowired
    DossierImpl(final Validation<DossierRequest> validation) {
        this.validation = validation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletionStage<Result<DossierResponse, ResultError>> call(final DossierRequest request) {

        logger.info("START: Dossier Service with Request: {}", request);

        return CompletableFuture.supplyAsync(() -> validateRequest(request).map(validatedRequest ->
                callBuilderExample(validatedRequest.getReferenceNumber()).map(applicationDtos -> {
                    logger.info("isAnyReferenceAvailable: {}", referenceExists(applicationDtos));
                    return generateResponse(validatedRequest);
                })));
    }

    /**
     * Performs input request validations as-well as any pre-processing.
     *
     * @param request the unmodified input request
     * @return the validated request, or else an error
     */
    final Result<DossierRequest, ResultError> validateRequest(final DossierRequest request) {

        return validation.validate(request);
    }

    final Result<List<ImmutableApplicationDto>, ResultError> callBuilderExample(final String referenceNumber) {

        final ImmutableApplicationDto dtoExample1 = ImmutableApplicationDto.builder()
                .referenceNumber("REF123")
                .build();

        final ImmutableApplicationDto dtoExample2 = ImmutableApplicationDto.builder()
                .referenceNumber("REF456")
                .build();

        if (StringUtils.hasText(referenceNumber) && referenceNumber.equals("REF_BREAK_BUILDER")) {
            return Result.error(DOS_002.toResultError());
        }

        return Result.success(List.of(dtoExample1, dtoExample2));
    }

    /**
     * Checks whether any reference number exists
     *
     * @param applicationsList a list of applications
     * @return true, if a refrence number is present, else false
     */
    final boolean referenceExists(final List<ImmutableApplicationDto> applicationsList) {

        return applicationsList
                .stream()
                .anyMatch(dto -> StringUtils.hasText(dto.getReferenceNumber()));
    }

    /**
     * Generate the Service Response
     *
     * @param request the validated request
     * @return Service Response, or else an error
     */
    private Result<DossierResponse, ResultError> generateResponse(final DossierRequest request) {

        return Result.success(ImmutableDossierResponse.builder()
                .referenceNumber(request.getReferenceNumber())
                .dossierClient(request.getClient())
                .products(request.getProducts())
                .build());
    }
}