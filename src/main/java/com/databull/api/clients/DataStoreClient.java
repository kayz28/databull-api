package com.databull.api.clients;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.response.DataBaseDetailsResponse;
import com.databull.api.dto.response.TableDetailsResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DataStoreClient {
    Boolean checkConnection() throws SQLException;
    public Connection connect();
    public void disconnect();

    List<String> getDatabasesList() throws SQLException;

    List<DataBaseDetailsResponse.TableDetails> getTablesList(String databaseName) throws SQLException;

    List<ColumnDetails> getColumnsList(String databaseName, String tableName) throws SQLException;
}
