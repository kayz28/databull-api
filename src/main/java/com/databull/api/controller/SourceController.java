package com.databull.api.controller;

import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.dto.response.CreatePipelineResponse;
import com.databull.api.dto.response.DataBaseDetailsResponse;
import com.databull.api.dto.response.DataStoreDetailsResponse;
import com.databull.api.dto.response.TableDetailsResponse;
import com.databull.api.repository.DataStoresRepository;
import com.databull.api.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
public class SourceController {
    private static final Logger log = LoggerFactory.getLogger(SourceController.class);

    @Autowired
    DataStoresService dataStoresService;

    @Autowired
    private DataStoresRepository dataStoresRepository;

    @Autowired
    DataPipelineServiceManager dataPipelineServiceManager;

    @GetMapping(value = "/sources/{type}", produces = "application/json")
    public ResponseEntity<List<DataStoreConfig>> getAllSourcesByType (@PathVariable String type) {
        try {
            System.out.println(type);
            List<DataStoreConfig> dataStoreConfigs = dataStoresService.getDataStoreConfigByType(type);
            return new ResponseEntity<>(dataStoreConfigs, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sources/{type}/databases/{id}", produces = "application/json")
    public ResponseEntity<DataStoreDetailsResponse> getAllDataBasesFromSource (@PathVariable Long id, @PathVariable String type) {
        try {
            DataStoreConfig dataStoreConfig = dataStoresService.getDataStoreConfigWithType(id, type);
            if(dataStoreConfig == null) throw new Exception("No such config present with given id and type.");
            return new ResponseEntity<>(dataStoresService.databaseList(id, type, dataStoreConfig), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            //todo write a controller advice
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sources/{type}/tables", produces = "application/json")
    public ResponseEntity<DataBaseDetailsResponse> getAllTablesFromSource (@RequestParam Long id,
                                                                           @PathVariable String type,
                                                                           @RequestParam String databaseName) {
        try {
            DataStoreConfig dataStoreConfig = dataStoresService.getDataStoreConfigWithType(id, type);
            if(dataStoreConfig == null) throw new Exception("No such config present with given id and type.");
            DataBaseDetailsResponse dataBaseDetailsResponse = dataStoresService.tablesDetails(id, type, dataStoreConfig,
                    databaseName);
            return new ResponseEntity<>(dataBaseDetailsResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            //todo write a controller advice
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sources/{type}/columns", produces = "application/json")
    public ResponseEntity<TableDetailsResponse> getAllColumnsFromSource (@RequestParam Long id,
                                                                           @PathVariable String type,
                                                                           @RequestParam String databaseName,
                                                                            @RequestParam String tableName) {
        try {
            DataStoreConfig dataStoreConfig = dataStoresService.getDataStoreConfigWithType(id, type);
            if(dataStoreConfig == null) throw new Exception("No such config present with given id and type.");
            TableDetailsResponse tableDetailsResponse = dataStoresService.columnDetails(id, type, dataStoreConfig,
                    databaseName, tableName);
            return new ResponseEntity<>(tableDetailsResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            //todo write a controller advice
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/source/{id}", produces = "application/json")
    public ResponseEntity<DataStoreConfig> getSource (@PathVariable Long id) {
        try {
            return new ResponseEntity<>(dataStoresService.getDataStoreConfig(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/create/pipeline", produces = "application/json")
    public ResponseEntity<CreatePipelineResponse> createPipeline (@RequestBody @Valid TableSyncRequest tableSyncRequest) {
        try {
            boolean dataStorePresent = dataStoresService.checkDataStorePresentById(tableSyncRequest.getDsId());
            if(!dataStorePresent) throw new Exception("DataStore not present");
            DataStoreConfig dataStoreConfig = dataStoresService.getDataStoreConfig(tableSyncRequest.getDsId());
            CreatePipelineResponse pipelineResponse = null;
            DataStorePipelineService dataStorePipelineService = dataPipelineServiceManager.routeToService(tableSyncRequest.getDsType());
            pipelineResponse = dataStorePipelineService.createMultiTablePipeline(tableSyncRequest, dataStoreConfig);
            return new ResponseEntity<>(pipelineResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Error while processing the request:" + e.getMessage());
            return new ResponseEntity<>(new CreatePipelineResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/health_check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
