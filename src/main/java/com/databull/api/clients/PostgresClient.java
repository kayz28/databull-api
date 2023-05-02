package com.databull.api.clients;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.DataStoreConfig;
import com.databull.api.constants.query.QueryConstants;
import com.databull.api.dto.response.DataBaseDetailsResponse;
import org.springframework.scheduling.annotation.Async;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgresClient extends DataStoreBase implements DataStoreClient{

    private static final String POSTGRES_DATABASE_DRIVER = "org.postgresql.Driver";

    public PostgresClient(DataStoreConfig dataStoreConfig) {
        super(dataStoreConfig, POSTGRES_DATABASE_DRIVER, "postgres");
    }

    @Override
    public Boolean checkConnection() throws SQLException {
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.POSTGRES_PING_QUERY);
            return true;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<String> getDatabasesList() throws SQLException {
        return null;
    }

    @Async
    public Boolean checkBinLogEnabled() throws SQLException {
        boolean result;
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.POSTGRES_WAL_ENABLE_QUERY);
            result = rst.getBoolean(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return result;
    }

    @Async
    public List<DataBaseDetailsResponse.TableDetails> getTablesList(String database) throws SQLException {
        List<String> tableList = new ArrayList<>();
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.POSTGRES_TABLE_DETAILS);
            while(rst.next()) {
                tableList.add(rst.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        //todo
        return null;
    }

    @Override
    public List<ColumnDetails> getColumnsList(String databaseName, String tableName) throws SQLException {
        return null;
    }

    @Async
    public List<String> getColumnsList(String columnName) throws SQLException {
        List<String> columnList = new ArrayList<>();
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.POSTGRES_COLUMN_DETAILS);
            while(rst.next()) {
                columnList.add(rst.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return columnList;
    }

}
