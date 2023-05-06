package com.databull.api.clients;

import com.databull.api.dto.DataStoreConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataStoreBase {

    private final String username;
    private final String endpoint;
    private final Integer port;
    //mysql, postgres, mongodb
    private String type;
    private Connection connection;
    private final String DATABASE_DRIVER;
    private Properties props;
    private String databaseName;


    public DataStoreBase(DataStoreConfig dataStoreConfig, String DATABASE_DRIVER, String type) {
        this.username = dataStoreConfig.getUsername();
        this.endpoint = dataStoreConfig.getEndpoint();
        this.port = dataStoreConfig.getPort();
        this.DATABASE_DRIVER = DATABASE_DRIVER;
        this.type = type;
        this.databaseName = dataStoreConfig.getDatabaseName();
        props = new Properties();
        props.put("user", dataStoreConfig.getUsername());
        props.put("password", dataStoreConfig.getPassword());

    }

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(constructDbUrl(), props);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private String constructDbUrl() {
        switch (type) {
            case "mysql" -> {
                return "jdbc:mysql://" + endpoint + ":" + port + "/" ;
            }
            case "postgres" -> {
                return "jdbc:postgresql://" + endpoint + ":" + port + "/" + this.databaseName;
            }
            case "mongodb" -> {
                return "jdbc:mongo://" + endpoint + ":" + port + "/";
            }
        }
        return null;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
