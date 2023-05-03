package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpTablesConfigurationBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "dp_tables_configurations", schema = "data_platform")
@Getter
@Setter
public class DpTablesConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    @Column(name = "destination_table_name", length = 100)
    private String destinationTableName;

    @Column(name = "incrementing_column", nullable = false, length = 100)
    private String incrementingColumn;

    @Column(name = "timestamp_column", length = 100)
    private String timestampColumn;

    @Column(name = "partitioning_column", length = 100)
    private String partitioningColumn;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "columns_whitelisting")
    private Integer columnsWhitelisting;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "data_store_id")
    private Long dataStoreId;

    @Column(name = "is_source_connector_created")
    private Boolean isSourceConnectorCreated;

    @Column(name = "is_sink_connector_created")
    private Boolean isSinkConnectorCreated;

    @Column(name = "first_sync_time")
    private Instant firstSyncTime;

    public static DpTablesConfigurationBuilder builder() {
        return DpTablesConfigurationBuilder.getInstance();
    }

}