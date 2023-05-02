package com.databull.api.service;

import com.databull.api.adaptors.validators.MysqlValidation;
import com.databull.api.clients.MysqlClient;
import com.databull.api.constants.query.RMQConstants;
import com.databull.api.dto.DataStoreConfig;
import com.databull.api.config.RMQRefinedConfig;
import com.databull.api.dto.DestinationDetails;
import com.databull.api.dto.response.CreatePipelineResponse;
import com.databull.api.entity.rmq.TableDetailPayload;
import com.databull.api.exception.CustomException;
import com.databull.api.dto.TableDetails;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.repository.DataStoresRepository;
import com.databull.api.repository.OrgRepository;
import com.databull.api.utils.rest.Pair;
import com.databull.api.utils.rest.Utils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

//datastore to datastore -- move to s3 then spark job
//datastore to warehouse -- s3
//datastore to data lake -- s3

public class MysqlDataPipelineService implements DataStorePipelineService {

    private static final Logger log = LoggerFactory.getLogger(MysqlDataPipelineService.class);

    @Autowired
    private DataStoresRepository dataStoresRepository;

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private RMQRefinedConfig rmqRefinedConfig;

    private final RabbitMQGenericProducer rabbitMQProducer;

    public MysqlDataPipelineService() throws Exception {
        rabbitMQProducer = new RabbitMQGenericProducer("json");
    }

    @Override
    public Boolean checkStatusTableSourceConnector(String dataStoreType, Integer tableId) {
        return null;
    }

    @Override
    public CreatePipelineResponse createMultiTablePipeline(TableSyncRequest tableSyncRequest, DataStoreConfig dataStoreConfig) throws Exception {
        //create table entry
        //launch connectors with source
        //push in queue according to destination
        //consumer logic
        //push in rmq
        //create connector source and sink
        //update if data landed on s3
        //create message
        if(Objects.isNull(tableSyncRequest))
            throw new IllegalArgumentException("TableSyncRequest is Empty. Please check");
        if(Objects.isNull(tableSyncRequest.getTableDetailsList()) || tableSyncRequest.getTableDetailsList().isEmpty())
            throw new IllegalArgumentException("Table details can't be empty");

        MysqlClient mysqlClient = new MysqlClient(dataStoreConfig);
        MysqlValidation.validateMysqlTableRequest(tableSyncRequest, mysqlClient);
        List<TableDetails> tableDetails = tableSyncRequest.getTableDetailsList();

        //equivalent to async await .join() is await method so Completablefuture.supplyasync is giving the
        //async functionality to executor threads we are writing async code in synchronous way

        List<String> results = CompletableFuture.supplyAsync(() -> {
                        try {
                            List<TableDetailPayload> tableDetailPayloadsList =
                                    createTableDetailPayload(tableDetails, tableSyncRequest, dataStoreConfig);
                            return tableDetailPayloadsList;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                }).handle((result, exp) -> {
                        if(exp != null) {
                            log.error("Error while creating table payload for mq push");
                            return null;
                        }
                        List<String> exceptionMap = new ArrayList<>();
                        result.forEach(tableDetailPayload -> {
                            try {
                                rabbitMQProducer.sendMessage(RMQConstants.CREATE_Q, tableDetailPayload);
                                rabbitMQProducer.sendMessage(RMQConstants.WORKER_Q, tableDetailPayload);

                            } catch (Exception e) {
                               exceptionMap.add(tableDetailPayload.getTableName());
                            }
            });
           return exceptionMap;
        }).join();

        if(Objects.isNull(results)) {
            throw new CustomException(new CustomException.ErrorMessage("Error while processing the payload",
                    500));
        }

        CreatePipelineResponse createPipelineResponse = new CreatePipelineResponse();
        createPipelineResponse.setMessage("Pipeline creation in process");
        createPipelineResponse.setFailedTableNameList(results);
        List<String> alltables = tableDetails.stream().map(TableDetails::getTableName).collect(Collectors.toList());
        alltables.removeAll(results);
        createPipelineResponse.setAcceptedTableNamesList(alltables);
        return createPipelineResponse;
    }

    private Function<Long, String> getOrgName = (orgId) -> orgRepository.findOrgNameById(orgId);

    private BiFunction<TableDetails, DestinationDetails, JSONObject> createSinkConnector = (tableDetail, destinationDetails) -> {
        try {
            Pair<String, Boolean> warehouseCheck = destinationDetails.getDataWareHouseTypeAndCheck();
            Pair<String, Boolean> dataStoreCheck = destinationDetails.getDataStoreTypeAndCheck();

            if(warehouseCheck.getSecond()) {
                //getConnector config
                //sink connector of s3
                JSONObject s3SinkConnector = Utils.getConnectorConfig("s3", "sink");
                s3SinkConnector.getJSONObject("config")
                        .put("s3.bucket.name", "mysql-default")
                        .put("topics", tableDetail.getTableName())
                        .put("name", "sink-s3-" + tableDetail.getTableName() + "-connector");

                s3SinkConnector.getJSONObject("name")
                        .put("name", "sink-s3-" + tableDetail.getTableName() + "-connector");
                return s3SinkConnector;
            }

            //Todo: implement s3 sink for Datastores
            else if (dataStoreCheck.getSecond()) {
                if(dataStoreCheck.getFirst().equals("mysql")) {

                } else if(dataStoreCheck.getFirst().equals("postgres")) {

                }
            }

        } catch (Exception e) {
            throw new CustomException(new CustomException.ErrorMessage("Error while processing connectors.",
                    500));
        }
        return null;
    };

//redo
    private BiFunction<TableSyncRequest, DataStoreConfig, JSONObject> createSourceConnector = (tableSyncRequest, dataStoreConfig) -> {
        JSONObject sourceConnector = null;
        try {
            sourceConnector = Utils.getConnectorConfig("s3", "sink");
            sourceConnector.getJSONObject("config")
                    .put("database.name", dataStoreConfig.getEndpoint())
                    .put("database.port", dataStoreConfig.getPort())
                    .put("database.user", dataStoreConfig.getUsername())
                    .put("database.include.list", tableSyncRequest.getDatabaseName())
                    .put("table.include.list", tableSyncRequest.getTableDetailsList().stream().map(TableDetails::getTableName));
            return sourceConnector;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Todo: kafka config for bootstrap server
        //                .put("");
    };


    private List<TableDetailPayload> createTableDetailPayload(List<TableDetails> tableDetails, TableSyncRequest tableSyncRequest, DataStoreConfig dataStoreConfig) throws IOException {
        List<TableDetailPayload> tableDetailPayloads = tableDetails.stream().map(tabledetail -> {
                TableDetailPayload tableDetailPayload = TableDetailPayload.builder()
                        .tableName(tabledetail.getTableName())
                        .dsId(tableSyncRequest.getDsId())
                        .databaseName(tabledetail.getDatabaseName())
                        .orgName(getOrgName.apply(tableSyncRequest.getOrgId()))
                        .uuid(UUID.randomUUID())
                        .columnDetailsList(tabledetail.getColumnDetailsList())
                        .timestamp(Instant.now())
                        .sinkConnector(createSinkConnector.apply(tabledetail, tableSyncRequest.getDestinationDetails()))
                        .sourceConnector(createSourceConnector.apply(tableSyncRequest, dataStoreConfig))
                        .destinationDetails(tableSyncRequest.getDestinationDetails())
                        .build();
                return tableDetailPayload;
            }).collect(Collectors.toList());
            return tableDetailPayloads;
    }



//consumer logic
//        Map<Long, String> tableIdMapping = saveTablesConfiguration(tableSyncRequest);
//        saveDestinationTableMetaData(tableSyncRequest, tableIdMapping);
//    private Map<Long, String> saveTablesConfiguration(TableSyncRequest tableSyncRequest) {
//        List<TableSyncRequest.TableDetails> tableDetails = tableSyncRequest.getTableDetailsList();
//        List<DpTablesConfiguration> tablesConfigurations = new ArrayList<>();
//        for(TableSyncRequest.TableDetails tableDetails1: tableDetails) {
//            DpTablesConfiguration dpTablesConfiguration = createTablesConfiguration.apply(tableSyncRequest, tableDetails1);
//            tablesConfigurations.add(dpTablesConfiguration);
//        }
//        Iterator<DpTablesConfiguration> itr = tablesConfigurationRepository.saveAll(tablesConfigurations).iterator();
//        Map<Long, String> tableIdToNameMapping = new HashMap<>();
//
//        while(itr.hasNext()) {
//            DpTablesConfiguration dpTablesConfiguration = itr.next();
//            tableIdToNameMapping.put(dpTablesConfiguration.getId(), dpTablesConfiguration.getDestinationTableName());
//        }
//
//        return tableIdToNameMapping;
//    }
//
//    private void saveDestinationTableMetaData(TableSyncRequest tableSyncRequest, Map<Long, String> tableIdMapping) {
//        List<DpDestinationTableMetadatum> dpDestinationTableMetadata = new ArrayList<>();
//
//        for(Map.Entry<Long, String> entry : tableIdMapping.entrySet()) {
//            DpDestinationTableMetadatum dpDestinationTableMetadatum = createDestinationTableMetaData.apply(entry);
//            dpDestinationTableMetadata.add(dpDestinationTableMetadatum);
//        }
//        destinationTableMetadataRepository.saveAll(dpDestinationTableMetadata);
//    }
//
//    BiFunction<TableSyncRequest, TableSyncRequest.TableDetails, DpTablesConfiguration> createTablesConfiguration =
//            (tableSyncRequest, tableDetails1) -> {
//                DpTablesConfiguration dpTablesConfiguration = DpTablesConfiguration.builder()
//                       .withTableName(tableDetails1.getTableName())
//                       .withDatabaseName(tableSyncRequest.getDatabaseName())
//                       .withCreatedAt(Instant.now())
//                       .withUpdatedAt(Instant.now())
//                       .withDataStoreId(tableSyncRequest.getDataStoreId())
//                       .withFirstSyncTime(null)
//                       .withDestinationTableName("dest_" + tableSyncRequest.getDatabaseName())
//                       .withIncrementingColumn(tableDetails1.getIncrementingColumn())
//                       .withTimestampColumn(tableDetails1.getTimestampColumn())
//                       .withPartitioningColumn(tableDetails1.getPartitionColumn())
//                       .withDatabaseName(tableSyncRequest.getDatabaseName())
//                       .withIsSinkConnectorCreated(false)
//                       .withIsSourceConnectorCreated(false)
//                       .withColumnsWhitelisting(0)
//                       .build();
//               return dpTablesConfiguration;
//            };
//
//    Function<Map.Entry<Long, String>, DpDestinationTableMetadatum> createDestinationTableMetaData =
//            (tableIdMappingEntry) -> {
//                DpDestinationTableMetadatum dpDestinationTableMetadatum = DpDestinationTableMetadatum.builder()
//                        .withDestinationTableName(tableIdMappingEntry.getValue())
//                        .withTableId(tableIdMappingEntry.getKey())
//                        .withCreatedAt(Instant.now())
//                        .withUpdatedAt(Instant.now())
//                        .withIsActive(false)
//                        .withS3BucketName("MYSQL-DEFAULT")
//                        .withSparkConf(new HashMap<>())
//                        .withLastMergeIncrement(null)
//                        .withS3PathSchema("s3://")
//                        .withS3PathTopic("s3://")
//                        .withUpdateFrequency(12)
//                        .build();
//
//                return dpDestinationTableMetadatum;
//            };

    public static MysqlDataPipelineService getInstance() throws Exception {
        return new MysqlDataPipelineService();
    }

}

