package com.databull.api.service;

import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.dto.response.CreatePipelineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PostgresDataPipelineService<T> implements DataStorePipelineService {

    private static final Logger log = LoggerFactory.getLogger(PostgresDataPipelineService.class);


    @Override
    public CreatePipelineResponse createMultiTablePipeline(TableSyncRequest tableSyncRequest, DataStoreConfig dataStoreConfig) throws Exception {
        return null;
    }

    @Override
    public Boolean checkStatusTableSourceConnector(String dataStoreType, Integer tableId) {
        return null;
    }

    public static PostgresDataPipelineService getInstance() {
        return new PostgresDataPipelineService();
    }
}
