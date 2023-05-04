package com.databull.api.service;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.TableDetails;
import com.databull.api.dto.requests.TableSyncRequest;
import com.databull.api.dto.response.CreatePipelineResponse;
import com.databull.api.entity.mysql.DpDestinationTableMetadatum;
import com.databull.api.entity.mysql.DpTablesConfiguration;
import com.databull.api.repository.DestinationTableMetadataRepository;
import com.databull.api.repository.TablesConfigurationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

//datastore to datastore -- move to s3 then spark job
//datastore to warehouse -- s3
//datastore to data lake -- s3

@Service
@Transactional
public class MysqlDataPipelineService implements DataStorePipelineService {

    private static final Logger log = LoggerFactory.getLogger(MysqlDataPipelineService.class);

    @Autowired
    TablesConfigurationRepository tablesConfigurationRepository;

    @Autowired
    DestinationTableMetadataRepository destinationTableMetadataRepository;

    @Override
    public CreatePipelineResponse createMultiTablePipeline(TableSyncRequest tableSyncRequest, DataStoreConfig dataStoreConfig)  {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        Map<Long, String> mp = saveTablesConfiguration(tableSyncRequest);
                        saveDestinationTableMetaData(tableSyncRequest, mp);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return "Processed";
                }).handle((result, exp) -> {
                CreatePipelineResponse createPipelineResponse1 = new CreatePipelineResponse();
                    if(exp!=null) {
                        log.error("Error while creating the metadata entry:" + exp.getMessage() );
                        createPipelineResponse1.setMessage("Error while processing the table request");
                    }
                    else if(result.equals("Processed"))
                        createPipelineResponse1.setMessage("Processed the request, Added the entry.");
                    return createPipelineResponse1;
                }).join();
    }

    private Map<Long, String> saveTablesConfiguration(TableSyncRequest tableSyncRequest) {
        List<TableDetails> tableDetails = tableSyncRequest.getTableDetailsList();
        List<DpTablesConfiguration> tablesConfigurations = new ArrayList<>();
        for(TableDetails tableDetails1: tableDetails) {
            DpTablesConfiguration dpTablesConfiguration = createTablesConfiguration.apply(tableSyncRequest, tableDetails1);
            tablesConfigurations.add(dpTablesConfiguration);
        }
        Iterator<DpTablesConfiguration> itr = tablesConfigurationRepository.saveAll(tablesConfigurations).iterator();
        Map<Long, String> tableIdToNameMapping = new HashMap<>();

        while(itr.hasNext()) {
            DpTablesConfiguration dpTablesConfiguration = itr.next();
            tableIdToNameMapping.put(dpTablesConfiguration.getId(), dpTablesConfiguration.getDestinationTableName());
        }

        return tableIdToNameMapping;
    }

    private void saveDestinationTableMetaData(TableSyncRequest tableSyncRequest, Map<Long, String> tableIdMapping) {
        List<DpDestinationTableMetadatum> dpDestinationTableMetadata = new ArrayList<>();

        for(Map.Entry<Long, String> entry : tableIdMapping.entrySet()) {
            DpDestinationTableMetadatum dpDestinationTableMetadatum = createDestinationTableMetaData.apply(entry);
            dpDestinationTableMetadata.add(dpDestinationTableMetadatum);
        }
        destinationTableMetadataRepository.saveAll(dpDestinationTableMetadata);
    }

    BiFunction<TableSyncRequest, TableDetails, DpTablesConfiguration> createTablesConfiguration =
            (tableSyncRequest, tableDetails1) -> {
                List<ColumnDetails> columnDetails = tableDetails1.getColumnDetailsList();
                String incrementingColumn = null;
                String timestampColumn = null;
                String partitionColumn = null;
                for(ColumnDetails columnDetails1 : columnDetails) {
                    if(!Objects.isNull(columnDetails1.getIsIncrementingColumn()) && columnDetails1.getIsIncrementingColumn())
                        incrementingColumn = columnDetails1.getColumnName();
                    if(!Objects.isNull(columnDetails1.getIsTimestampColumn()) && columnDetails1.getIsTimestampColumn())
                        timestampColumn = columnDetails1.getColumnName();
                    if(!Objects.isNull(columnDetails1.getIsPartitionColumn()) && columnDetails1.getIsPartitionColumn())
                        partitionColumn = columnDetails1.getColumnName();
                }

                DpTablesConfiguration dpTablesConfiguration = DpTablesConfiguration.builder()
                        .withTableName(tableDetails1.getTableName())
                        .withDatabaseName(tableSyncRequest.getDatabaseName())
                        .withCreatedAt(Instant.now())
                        .withUpdatedAt(Instant.now())
                        .withDataStoreId(tableSyncRequest.getDsId())
                        .withFirstSyncTime(null)
                        .withDestinationTableName("dest_" + tableDetails1.getTableName())
                        .withIncrementingColumn(incrementingColumn)
                        .withTimestampColumn(timestampColumn)
                        .withPartitioningColumn(partitionColumn)
                        .withDatabaseName(tableSyncRequest.getDatabaseName())
                        .withIsSinkConnectorCreated(false)
                        .withIsSourceConnectorCreated(false)
                        .withColumnsWhitelisting(0)
                        .build();
                return dpTablesConfiguration;
            };

    Function<Map.Entry<Long, String>, DpDestinationTableMetadatum> createDestinationTableMetaData =
            (tableIdMappingEntry) -> {
                DpDestinationTableMetadatum dpDestinationTableMetadatum = DpDestinationTableMetadatum.builder()
                        .withDestinationTableName(tableIdMappingEntry.getValue())
                        .withTableId(tableIdMappingEntry.getKey())
                        .withCreatedAt(Instant.now())
                        .withUpdatedAt(Instant.now())
                        .withIsActive(false)
                        .withS3BucketName("MYSQL-DEFAULT")
                        .withSparkConf(new HashMap<>())
                        .withLastMergeIncrement(null)
                        .withS3PathSchema("s3://")
                        .withS3PathTopic("s3://")
                        .withUpdateFrequency(12)
                        .build();

                return dpDestinationTableMetadatum;
            };


    @Override
    public Boolean checkStatusTableSourceConnector(String dataStoreType, Integer tableId) {
        return null;
    }

}

