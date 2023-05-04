package com.databull.api.service;

import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.dto.response.CreatePipelineResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostgresDataPipelineService implements DataStorePipelineService {

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
