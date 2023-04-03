package com.alliander.kv.services.impl.dossier;

import com.alliander.kv.services.impl.dossier.beans.ImmutableApplicationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.alliander.kv.common.Common.assertError;
import static com.alliander.kv.common.Common.assertSuccess;
import static com.alliander.kv.services.impl.dossier.DossierErrors.DOS_002;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DossierImplTest {

    @Autowired
    private DossierImpl impl;

    @Test
    void test_callBuilderExample_DOS_002() {
        final String referenceNumber = "REF_BREAK_BUILDER";
        assertError(impl.callBuilderExample(referenceNumber), DOS_002);
    }

    @Test
    void test_callBuilderExample_Success() {
        final String referenceNumber = "REF_1";
        assertSuccess(impl.callBuilderExample(referenceNumber));
    }

    @Test
    void test_referenceExists_True() {

        final ImmutableApplicationDto dto = ImmutableApplicationDto.builder()
                .referenceNumber("REF_1")
                .build();

        assertTrue(impl.referenceExists(List.of(dto)));
    }
}