package com.databull.api.adaptors.validators;

import com.databull.api.clients.MysqlClient;
import com.databull.api.dto.TableDetails;
import com.databull.api.dto.requests.TableSyncRequest;

import java.util.List;
import java.util.stream.Collectors;

public class MysqlValidation {
//
//    public Boolean validateDatabaseConnection(MysqlClient mysqlClient) {
//
//    }
//
//    public Boolean checkBinLogEnabled(MysqlClient mysqlClient) {
//
//    }

    public void checkMysqlDataStoreValidations(MysqlClient mysqlClient) throws Exception {

        if(!mysqlClient.checkBinLogEnabled()) {
            throw new Exception("Error! Bin logs are not enabled for the datastore.");
        }

    }

    public void validateMysqlTableRequest(TableSyncRequest tableSyncRequest,
                                                 MysqlClient mysqlClient)
            throws Exception {
        if(!mysqlClient.checkConnection())
            throw new Exception("Not able to create a connection.");

        if(!mysqlClient.checkIfDataBasePresent(tableSyncRequest.getDatabaseName()))
            throw new Exception("Database not present.Please check the data source.");

        List<String> tableNames = tableSyncRequest.getTableDetailsList()
                .stream()
                .map(TableDetails::getTableName)
                .collect(Collectors.toList());

        if(!mysqlClient.checkIfTablesPresent(tableSyncRequest.getDatabaseName(), tableNames))
            throw new Exception("Given Tables not present in the source. Please try again or check if they are present " +
                    "on source side.");

    }


}
