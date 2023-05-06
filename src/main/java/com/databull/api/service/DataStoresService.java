package com.databull.api.service;

import com.databull.api.clients.DataStoreClient;
import com.databull.api.clients.MysqlClient;
import com.databull.api.clients.PostgresClient;
import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.DataStoreConfig;
import com.databull.api.dto.response.DataBaseDetailsResponse;
import com.databull.api.dto.response.DataStoreDetailsResponse;
import com.databull.api.dto.response.TableDetailsResponse;
import com.databull.api.entity.mysql.DpDataStoresConfiguration;
import com.databull.api.repository.DataStoresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataStoresService {
    private static final Logger logger = LoggerFactory.getLogger(DataStoresService.class);

    @Autowired
    private DataStoresRepository dataStoresRepository;

//    @Autowired
//    private RMQRefinedConfig rmqRefinedConfig;
//
//    public DataStoresService() {
//        rabbitMQProducer = new RabbitMQGenericProducer("json");
//    }

    private DataStoreClient getDataStoreClient(String type, DataStoreConfig dataStoreConfig) {
        if(type.equals("mysql")) {
            return new MysqlClient(dataStoreConfig);
        } else if(type.equals("postgres")) {
            return new PostgresClient(dataStoreConfig);
        } else if(type.equals("mongodb")) {
           //todo:
        }
        return null;
    }


    public boolean checkDataStorePresentById(Long id) {
        return dataStoresRepository.existsById(id);
    }

    public DataStoreConfig getDataStoreConfig(Long id) {
        Optional<DpDataStoresConfiguration> dpDataStoresConfiguration = dataStoresRepository.findById(id);
        DataStoreConfig dataStoreConfig = new DataStoreConfig();
        dpDataStoresConfiguration.ifPresent(
                (dpDataStoresConfigurations) -> {
                    dataStoreConfig.setEndpoint(dpDataStoresConfigurations.getEndpoint());
                    dataStoreConfig.setPort(dpDataStoresConfigurations.getPort());
                    dataStoreConfig.setPassword(dpDataStoresConfigurations.getPassword());
                    dataStoreConfig.setUsername(dpDataStoresConfigurations.getUsername());

                }
        );
        return dataStoreConfig;
    }

    public DataStoreConfig getDataStoreConfigWithType(Long id, String type) {
        DpDataStoresConfiguration dpDataStoresConfiguration1 = dataStoresRepository.findByIdAndSourceType(id, type);
        DataStoreConfig dataStoreConfig = new DataStoreConfig();
        dataStoreConfig.setEndpoint(dpDataStoresConfiguration1.getEndpoint());
        dataStoreConfig.setPort(dpDataStoresConfiguration1.getPort());
        dataStoreConfig.setPassword(dpDataStoresConfiguration1.getPassword());
        dataStoreConfig.setUsername(dpDataStoresConfiguration1.getUsername());
        return dataStoreConfig;
    }

    public List<DataStoreConfig> getDataStoreConfigByType(String type) {
        List<DpDataStoresConfiguration> dpDataStoresConfiguration1 = dataStoresRepository.findBySourceType(type);
        List<DataStoreConfig> dataStoreConfigs = new ArrayList<>();
        for (DpDataStoresConfiguration dpDataStoresConfiguration : dpDataStoresConfiguration1) {
            DataStoreConfig dataStoreConfig = new DataStoreConfig();
            dataStoreConfig.setEndpoint(dpDataStoresConfiguration.getEndpoint());
            dataStoreConfig.setPort(dpDataStoresConfiguration.getPort());
            dataStoreConfig.setPassword(dpDataStoresConfiguration.getPassword());
            dataStoreConfig.setUsername(dpDataStoresConfiguration.getUsername());
            dataStoreConfigs.add(dataStoreConfig);
        }
        return dataStoreConfigs;
    }

//    public DataBaseDetailsResponse databaseList(Long id, String type, DataStoreConfig dataStoreConfig) throws SQLException {
//        MysqlClient mysqlClient = new MysqlClient(dataStoreConfig);
//        DataBaseDetailsResponse dataBaseDetailsResponse = new DataBaseDetailsResponse();
//        dataBaseDetailsResponse.setDatabaseNameList(mysqlClient.getDatabasesList());
//        dataBaseDetailsResponse.setDsId(id);
//        dataBaseDetailsResponse.setDsType(type);
//        dataBaseDetailsResponse.setDbCount(mysqlClient.getDatabasesList().size());
//        return dataBaseDetailsResponse;
//    }

    public DataStoreDetailsResponse databaseList(Long id, String type, DataStoreConfig dataStoreConfig) throws SQLException {
        DataStoreClient dataStoreClient = getDataStoreClient(type, dataStoreConfig);
        DataStoreDetailsResponse dataBaseDetailsResponse = new DataStoreDetailsResponse();
        dataBaseDetailsResponse.setDatabaseNameList(dataStoreClient.getDatabasesList());
        dataBaseDetailsResponse.setDsId(id);
        dataBaseDetailsResponse.setDsType(type);
        dataBaseDetailsResponse.setDbCount(dataStoreClient.getDatabasesList().size());
        return dataBaseDetailsResponse;
    }

    public DataBaseDetailsResponse tablesDetails(Long id, String type, DataStoreConfig dataStoreConfig, String databaseName) throws SQLException {
        DataStoreClient dataStoreClient = getDataStoreClient(type, dataStoreConfig);
        DataBaseDetailsResponse dataBaseDetailsResponse = new DataBaseDetailsResponse();
        dataBaseDetailsResponse.setDsId(id);
        assert dataStoreClient != null;
        System.out.println(dataStoreClient.getTablesList(databaseName));
        dataBaseDetailsResponse.setTableDetailsList(dataStoreClient.getTablesList(databaseName));
        dataBaseDetailsResponse.setDatabaseName(databaseName);
        return dataBaseDetailsResponse;
    }

    public TableDetailsResponse columnDetails(Long id, String type, DataStoreConfig dataStoreConfig, String databaseName,
                                                 String tableName) throws Exception {
        DataStoreClient dataStoreClient = getDataStoreClient(type, dataStoreConfig);
        TableDetailsResponse tableDetailsResponse = new TableDetailsResponse();
        assert dataStoreClient != null;
        List<ColumnDetails> columnDetails = dataStoreClient.getColumnsList(databaseName, tableName);
        tableDetailsResponse.setColumnDetails(columnDetails);
        tableDetailsResponse.setDsId(id);
        tableDetailsResponse.setDbName(databaseName);
        tableDetailsResponse.setTableName(tableName);
        tableDetailsResponse.setDsType(type);
        tableDetailsResponse.setNumberOfColumns((long) columnDetails.size());
        //for testing purpose
        return tableDetailsResponse;
    }


}
