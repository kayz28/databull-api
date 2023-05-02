package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpDataStoresConfiguration;

import java.time.Instant;

public final class DpDataStoresConfigurationBuilder {
    private Long id;
    private String endpoint;
    private String username;
    private String password;
    private Integer port;
    private Boolean setupDone;
    private Boolean logReplicationEnabled;
    private Integer userId;
    private Integer orgId;
    private Integer cloudDataStoresId;
    private Instant createdAt;
    private Instant updatedAt;

    private DpDataStoresConfigurationBuilder() {
    }

    public static DpDataStoresConfigurationBuilder getInstance() {
        return new DpDataStoresConfigurationBuilder();
    }

    public DpDataStoresConfigurationBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpDataStoresConfigurationBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public DpDataStoresConfigurationBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DpDataStoresConfigurationBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DpDataStoresConfigurationBuilder withPort(Integer port) {
        this.port = port;
        return this;
    }

    public DpDataStoresConfigurationBuilder withSetupDone(Boolean setupDone) {
        this.setupDone = setupDone;
        return this;
    }

    public DpDataStoresConfigurationBuilder withLogReplicationEnabled(Boolean logReplicationEnabled) {
        this.logReplicationEnabled = logReplicationEnabled;
        return this;
    }

    public DpDataStoresConfigurationBuilder withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public DpDataStoresConfigurationBuilder withOrgId(Integer orgId) {
        this.orgId = orgId;
        return this;
    }

    public DpDataStoresConfigurationBuilder withCloudDataStoresId(Integer cloudDataStoresId) {
        this.cloudDataStoresId = cloudDataStoresId;
        return this;
    }

    public DpDataStoresConfigurationBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DpDataStoresConfigurationBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DpDataStoresConfiguration build() {
        DpDataStoresConfiguration dpDataStoresConfiguration = new DpDataStoresConfiguration();
        dpDataStoresConfiguration.setId(id);
        dpDataStoresConfiguration.setEndpoint(endpoint);
        dpDataStoresConfiguration.setUsername(username);
        dpDataStoresConfiguration.setPassword(password);
        dpDataStoresConfiguration.setPort(port);
        dpDataStoresConfiguration.setSetupDone(setupDone);
        dpDataStoresConfiguration.setLogReplicationEnabled(logReplicationEnabled);
        dpDataStoresConfiguration.setUserId(userId);
        dpDataStoresConfiguration.setOrgId(orgId);
        dpDataStoresConfiguration.setCloudDataStoresId(cloudDataStoresId);
        dpDataStoresConfiguration.setCreatedAt(createdAt);
        dpDataStoresConfiguration.setUpdatedAt(updatedAt);
        return dpDataStoresConfiguration;
    }
}
