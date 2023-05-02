package com.databull.api.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

enum DATASTORE {
    MYSQL,
    POSTGRES,
    MONGODB
}

@Service
public class DataStorePipelineServiceManager {
    private final Map<DATASTORE, DataStorePipelineService> serviceMap;

    public DataStorePipelineServiceManager() throws Exception {
        this.serviceMap = new HashMap<>();
        init();
    }

    public DataStorePipelineService getServiceFromEnum(String dataStoreType) throws Exception {
        switch (DATASTORE.valueOf(dataStoreType)) {
            case MYSQL -> {
                return MysqlDataPipelineService.getInstance();
            }
            case POSTGRES -> {
                return PostgresDataPipelineService.getInstance();
            }
            case MONGODB -> {
                return MongoDbDataPipelineService.getInstance();
            }
            default -> {
                return null;
            }
        }
    }

    private void init() throws Exception {
        DATASTORE[] datastores = DATASTORE.values();
        for (DATASTORE datastore : datastores) {
            serviceMap.put(datastore, getServiceFromEnum(datastore.toString()));
        }
    }

    public DataStorePipelineService routeToService(String dataStoreType) {
        return serviceMap.get(DATASTORE.valueOf(dataStoreType.toUpperCase().trim()));
    }
}
