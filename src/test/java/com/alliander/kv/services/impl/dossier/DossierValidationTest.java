package com.alliander.kv.services.impl.dossier;

import com.alliander.kv.common.ValidationTest;
import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import com.alliander.kv.services.api.dossier.beans.DossierProduct;
import com.alliander.kv.services.api.dossier.beans.DossierRequest;
import com.alliander.kv.services.api.dossier.beans.ImmutableDossierProduct;
import com.alliander.kv.services.api.dossier.beans.ImmutableDossierRequest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.alliander.kv.common.Common.addRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class DossierValidationTest implements ValidationTest<DossierRequest> {

    @Autowired
    private DossierValidation validation;

    @Override
    public Stream<Arguments> buildTestCases() {

        return Stream.of(
                addRequest(getSuccessCaseBuilder()
                        .build(), "SUCCESS"),
                addRequest(getSuccessCaseBuilder()
                        .referenceNumber(null)
                        .build(), "DOS_001"),
                addRequest(getSuccessCaseBuilder()
                        .products(null)
                        .build(), "DOS_003"),
                addRequest(getSuccessCaseBuilder()
                        .referenceNumber("REF_001")
                        .build(), "DOS_004"),
                addRequest(getSuccessCaseBuilder()
                        .referenceNumber(null)
                        .products(null)
                        .build(), "DOS_003,DOS_001")
        );
    }

    /**
     * Generates a builder of the request object with the appropriate values for a success case.
     *
     * @return a valid request builder
     */
    private static ImmutableDossierRequest.Builder getSuccessCaseBuilder() {

        final List<DossierProduct> products = List.of(getSuccessCaseDossierProduct());

        return ImmutableDossierRequest.builder()
                .referenceNumber("REF001")
                .addAllProducts(products);
    }

    private static DossierProduct getSuccessCaseDossierProduct() {
        return ImmutableDossierProduct.builder()
                .name("Test Name")
                .description("Test Description")
                .build();
    }

    @ParameterizedTest
    @MethodSource("buildTestCases")
    @Override
    public void performValidation(final DossierRequest request, final String expectedErrorCode) {

        final Result<DossierRequest, ResultError> result = validation.validate(request);

        Optional.ofNullable(result.getError())
                .ifPresentOrElse(error -> assertEquals(result.getError().getCodes(), expectedErrorCode)
                        , () -> assertEquals("SUCCESS", expectedErrorCode));
    }
}