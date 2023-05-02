package com.databull.api.clients;

import com.databull.api.dto.DataStoreConfig;
import com.databull.api.constants.query.QueryConstants;
import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.response.DataBaseDetailsResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlClient extends DataStoreBase implements DataStoreClient {

    private static final String MYSQL_DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";

    public MysqlClient(DataStoreConfig dataStoreConfig) {
        super(dataStoreConfig, MYSQL_DATABASE_DRIVER, "mysql");
    }



    @Override
    public Boolean checkConnection() throws SQLException {
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.MYSQL_PING_QUERY);
            return true;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Boolean checkBinLogEnabled() throws SQLException {
        boolean result;
        try {
            Statement stmt = this.connect().createStatement();
            ResultSet rst = stmt.executeQuery(QueryConstants.MYSQL_BIN_LOG_QUERY);
            result = rst.getBoolean(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return result;
    }

    public List<DataBaseDetailsResponse.TableDetails> getTablesList(String database) throws SQLException {
        List<DataBaseDetailsResponse.TableDetails> tableList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.MYSQL_TABLE_DETAILS);
            stmt.setString(1, database);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()) {
                DataBaseDetailsResponse.TableDetails  tableDetails = new DataBaseDetailsResponse.TableDetails();
                tableDetails.setTableName(rst.getString("TABLE_NAME"));
                tableDetails.setNumberOfRecords(rst.getLong("TABLE_ROWS"));
                tableDetails.setTableComment(rst.getString("TABLE_COMMENT"));
                tableList.add(tableDetails);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return tableList;
    }

    public List<String> getDatabasesList() throws SQLException {
        List<String> databaseList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.MYSQL_DATABASE_QUERY);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()) {
               databaseList.add(rst.getString("schema_name"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return databaseList;
    }

    public List<ColumnDetails> getColumnsList(String databaseName, String tableName) throws SQLException {
        List<ColumnDetails> columnList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.MYSQL_COLUMN_DETAILS);
            stmt.setString(1, tableName);
            stmt.setString(2, databaseName);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()) {
                ColumnDetails columnDetails = new ColumnDetails();
                columnDetails.setColumnComment(rst.getString("COLUMN_COMMENT"));
                columnDetails.setColumnName(rst.getString("COLUMN_NAME"));
                columnDetails.setColumnType(rst.getString("COLUMN_TYPE"));
                columnDetails.setDataType(rst.getString("DATA_TYPE"));
                columnDetails.setIsNullable(rst.getString("IS_NULLABLE"));
                columnList.add(columnDetails);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return columnList;
    }

    public boolean checkIfDataBasePresent(String databaseName) throws Exception {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.MYSQL_DATABASE_CHECK_QUERY);
            stmt.setString(1, databaseName);
            ResultSet rst = stmt.executeQuery();
            if(rst.first()) return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;
    }

    public boolean checkIfTablesPresent(String databaseName, List<String> tableNamesList) throws Exception {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(QueryConstants.MYSQL_TABLE_DETAILS);
            stmt.setString(1, databaseName);
            ResultSet rst = stmt.executeQuery();
            List<String> lis = new ArrayList<>();
            while (rst.next()) {
                lis.add(rst.getString("TABLE_NAME"));
            }
            if(lis.contains(tableNamesList)) return true;
        } catch (SQLException exp) {
            throw new Exception(exp.getMessage());
        }
        return false;
    }

}
