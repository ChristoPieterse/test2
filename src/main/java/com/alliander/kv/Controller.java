package com.alliander.kv;

import com.alliander.kv.services.api.dossier.DossierService;
import com.alliander.kv.services.api.dossier.beans.DossierRequest;
import com.alliander.kv.services.serialization.dossier.DossierSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alliander.kv.common.serialization.JsonSerialization.processResponse;

@RestController
@RequestMapping("/api")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    final DossierService dossierService;
    final DossierSerialization serialization;

    @Autowired
    public Controller(final DossierService dossierService,
                      final DossierSerialization serialization) {
        this.dossierService = dossierService;
        this.serialization = serialization;
    }

    @PostMapping(value = "/v1/dossier", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> callDossierServiceV1(@RequestBody final String request) {
        final DossierRequest deserializeRequest = serialization.deserialize(request);
        final ResponseEntity<Object> response = processResponse(dossierService.call(deserializeRequest), serialization);
        logger.info("END: Dossier Service with Response: {}", response);
        return response;
    }
}
