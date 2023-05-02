package com.databull.api.service;

import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.dto.response.CreatePipelineResponse;

public interface DataStorePipelineService {
    CreatePipelineResponse createMultiTablePipeline(TableSyncRequest tableSyncRequest, DataStoreConfig dataStoreConfig)
        throws Exception;

    Boolean checkStatusTableSourceConnector(String dataStoreType, Integer tableId);
}
