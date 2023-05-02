package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpDataStoresConfigurationBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dp_databases_configuration", schema = "data_platform")
@Getter
@Setter
public class DpDatabasesConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "database_name", nullable = false, length = 100)
    private String databaseName;

    @Column(name = "data_store_id")
    private Integer dataStoreId;

    public static DpDataStoresConfigurationBuilder builder() {
        return DpDataStoresConfigurationBuilder.getInstance();
    }
}