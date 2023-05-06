package com.databull.api.constants.query;

public class QueryConstants {
    //Mysql queries
    public static final String MYSQL_DATABASE_DETAILS = "select * from information_schema where table_schema = ?";
    public static final String MYSQL_TABLE_DETAILS = "select * from information_schema.tables where table_schema = ?";
    public static final String MYSQL_COLUMN_DETAILS = "select * from information_schema.columns where table_name = ? and table_schema = ?";
    public static final String MYSQL_PING_QUERY = "select 1";
    public static final String MYSQL_BIN_LOG_QUERY = "show variables like \"log_bin\"";
    public static final String MYSQL_DATABASE_CHECK_QUERY = "SELECT schema_name FROM information_schema.schemata where SCHEMA_NAME = ?";
    public static final String MYSQL_DATABASE_QUERY = "SELECT schema_name FROM information_schema.schemata";

    //Postgres queries
    public static final String POSTGRES_DATABASE_DETAILS = "SELECT datname FROM pg_database";
    public static final String POSTGRES_TABLE_DETAILS = "select * from information_schema.tables where table_schema = ?"; //public schema name
    public static final String POSTGRES_COLUMN_DETAILS = "select * from information_schema.columns where table_schema = ? and table_name = ?"; // public and tablename
    public static final String POSTGRES_PING_QUERY = "Select 1";
    public static final String POSTGRES_WAL_ENABLE_QUERY = " ";

    //Mongodb queries
    public static final String MONGODB_DATABASE_DETAILS = "";
    public static final String MONGODB_COLLECTION_DETAILS = " ";
    public static final String MONGODB_SCHEMA_DETAILS = " ";
    public static final String MONGODB_PING_QUERY = " ";
    public static final String MONGODB_OP_LOG_QUERY = " ";


}
