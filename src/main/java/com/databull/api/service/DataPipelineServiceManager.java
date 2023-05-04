package com.databull.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataPipelineServiceManager {

    @Autowired
    MysqlDataPipelineService mysqlDataPipelineService;

    @Autowired
    PostgresDataPipelineService postgresDataPipelineService;

    @Autowired
    MongoDbDataPipelineService mongoDbDataPipelineService;

    public DataStorePipelineService routeToService(String datastoretype) {
        if(datastoretype.equals("mysql"))
            return mysqlDataPipelineService;
        else if(datastoretype.equals("postgres"))
            return postgresDataPipelineService;
        else return mongoDbDataPipelineService;
    }
}
