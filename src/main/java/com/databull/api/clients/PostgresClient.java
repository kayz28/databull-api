package com.databull.api.clients;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.DataStoreConfig;
import com.databull.api.constants.query.QueryConstants;
import com.databull.api.dto.response.DataBaseDetailsResponse;
import org.springframework.scheduling.annotation.Async;

import java.sql.PreparedStatement;
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
       List<String> databaseList = new ArrayList<>();
       try {
           PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.POSTGRES_DATABASE_DETAILS);
           ResultSet rst = stmt.executeQuery();
           while (rst.next()) {
               databaseList.add(rst.getString("datname"));
           }
       } catch (SQLException e) {
           throw new SQLException(e);
       }
       return databaseList;
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
        List<DataBaseDetailsResponse.TableDetails> tableList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.POSTGRES_TABLE_DETAILS);
            stmt.setString(1, database);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()) {
                DataBaseDetailsResponse.TableDetails  tableDetails = new DataBaseDetailsResponse.TableDetails();
                tableDetails.setTableName(rst.getString("table_name"));
                tableList.add(tableDetails);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return tableList;
    }

    @Override
    public List<ColumnDetails> getColumnsList(String databaseName, String tableName) throws SQLException {
        List<ColumnDetails> columnList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.POSTGRES_COLUMN_DETAILS);
            stmt.setString(1, databaseName);
            stmt.setString(2, tableName);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()) {
                ColumnDetails columnDetails = new ColumnDetails();
                columnDetails.setColumnName(rst.getString("column_name"));
                columnDetails.setColumnType(rst.getString("data_type"));
                columnDetails.setDataType(rst.getString("udt_name"));
                columnDetails.setIsNullable(rst.getString("is_nullable"));
                columnList.add(columnDetails);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return columnList;
    }

}
