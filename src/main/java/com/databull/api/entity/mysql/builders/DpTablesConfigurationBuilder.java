package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpTablesConfiguration;

import java.time.Instant;

public final class DpTablesConfigurationBuilder {
    private Long id;
    private String tableName;
    private String destinationTableName;
    private String incrementingColumn;
    private String timestampColumn;
    private String partitioningColumn;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer columnsWhitelisting;
    private String databaseName;
    private Long dataStoreId;
    private Boolean isSourceConnectorCreated;
    private Boolean isSinkConnectorCreated;
    private Instant firstSyncTime;

    private DpTablesConfigurationBuilder() {
    }

    public static DpTablesConfigurationBuilder getInstance() {
        return new DpTablesConfigurationBuilder();
    }

    public DpTablesConfigurationBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpTablesConfigurationBuilder withTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DpTablesConfigurationBuilder withDestinationTableName(String destinationTableName) {
        this.destinationTableName = destinationTableName;
        return this;
    }

    public DpTablesConfigurationBuilder withIncrementingColumn(String incrementingColumn) {
        this.incrementingColumn = incrementingColumn;
        return this;
    }

    public DpTablesConfigurationBuilder withTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
        return this;
    }

    public DpTablesConfigurationBuilder withPartitioningColumn(String partitioningColumn) {
        this.partitioningColumn = partitioningColumn;
        return this;
    }

    public DpTablesConfigurationBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DpTablesConfigurationBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DpTablesConfigurationBuilder withColumnsWhitelisting(Integer columnsWhitelisting) {
        this.columnsWhitelisting = columnsWhitelisting;
        return this;
    }

    public DpTablesConfigurationBuilder withDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public DpTablesConfigurationBuilder withDataStoreId(Long dataStoreId) {
        this.dataStoreId = dataStoreId;
        return this;
    }

    public DpTablesConfigurationBuilder withIsSourceConnectorCreated(Boolean isSourceConnectorCreated) {
        this.isSourceConnectorCreated = isSourceConnectorCreated;
        return this;
    }

    public DpTablesConfigurationBuilder withIsSinkConnectorCreated(Boolean isSinkConnectorCreated) {
        this.isSinkConnectorCreated = isSinkConnectorCreated;
        return this;
    }

    public DpTablesConfigurationBuilder withFirstSyncTime(Instant firstSyncTime) {
        this.firstSyncTime = firstSyncTime;
        return this;
    }

    public DpTablesConfiguration build() {
        DpTablesConfiguration dpTablesConfiguration = new DpTablesConfiguration();
        dpTablesConfiguration.setId(id);
        dpTablesConfiguration.setTableName(tableName);
        dpTablesConfiguration.setDestinationTableName(destinationTableName);
        dpTablesConfiguration.setIncrementingColumn(incrementingColumn);
        dpTablesConfiguration.setTimestampColumn(timestampColumn);
        dpTablesConfiguration.setPartitioningColumn(partitioningColumn);
        dpTablesConfiguration.setCreatedAt(createdAt);
        dpTablesConfiguration.setUpdatedAt(updatedAt);
        dpTablesConfiguration.setColumnsWhitelisting(columnsWhitelisting);
        dpTablesConfiguration.setDatabaseName(databaseName);
        dpTablesConfiguration.setDataStoreId(dataStoreId);
        dpTablesConfiguration.setIsSourceConnectorCreated(isSourceConnectorCreated);
        dpTablesConfiguration.setIsSinkConnectorCreated(isSinkConnectorCreated);
        dpTablesConfiguration.setFirstSyncTime(firstSyncTime);
        return dpTablesConfiguration;
    }
}
