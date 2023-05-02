package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpDatabasesConfiguration;

public final class DpDatabasesConfigurationBuilder {
    private Long id;
    private String databaseName;
    private Integer dataStoreId;

    private DpDatabasesConfigurationBuilder() {
    }

    public static DpDatabasesConfigurationBuilder getInstance() {
        return new DpDatabasesConfigurationBuilder();
    }

    public DpDatabasesConfigurationBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpDatabasesConfigurationBuilder withDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public DpDatabasesConfigurationBuilder withDataStoreId(Integer dataStoreId) {
        this.dataStoreId = dataStoreId;
        return this;
    }

    public DpDatabasesConfiguration build() {
        DpDatabasesConfiguration dpDatabasesConfiguration = new DpDatabasesConfiguration();
        dpDatabasesConfiguration.setId(id);
        dpDatabasesConfiguration.setDatabaseName(databaseName);
        dpDatabasesConfiguration.setDataStoreId(dataStoreId);
        return dpDatabasesConfiguration;
    }
}
