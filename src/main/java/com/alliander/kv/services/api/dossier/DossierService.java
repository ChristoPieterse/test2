package com.alliander.kv.services.api.dossier;

import com.alliander.kv.common.result.Result;
import com.alliander.kv.common.result.ResultError;
import com.alliander.kv.services.api.dossier.beans.DossierRequest;
import com.alliander.kv.services.api.dossier.beans.DossierResponse;

import java.util.concurrent.CompletionStage;

public interface DossierService {

    /**
     * The Dossier Service V1
     *
     * @param request the unmodified input request
     * @return Service response, or else an error
     */
    CompletionStage<Result<DossierResponse, ResultError>> call(DossierRequest request);
}