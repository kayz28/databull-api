package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpDataStoresConfigurationBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "dp_data_stores_configurations", schema = "data_platform")
@Getter
@Setter
public class DpDataStoresConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "endpoint", nullable = false, length = 100)
    private String endpoint;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "port", nullable = false)
    private Integer port;

    @Column(name = "setup_done")
    private Boolean setupDone;

    @Column(name = "log_replication_enabled", nullable = false)
    private Boolean logReplicationEnabled;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "org_id", nullable = false)
    private Integer orgId;

    @Column(name = "cloud_datastores_id", nullable = false)
    private Integer cloudDataStoresId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "can_be_warehouse_destination")
    private Boolean canBeWarehouseForDestination;

    @Column(name = "can_be_datastore_destination")
    private Boolean canBeDataStoreForDestination;

    @Column(name = "datastore_type")
    private String type;

    public static DpDataStoresConfigurationBuilder builder() {
        return DpDataStoresConfigurationBuilder.getInstance();
    }


}