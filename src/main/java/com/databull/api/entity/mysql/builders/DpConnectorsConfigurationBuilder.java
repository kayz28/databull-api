package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpConnectorsConfiguration;

import java.util.Map;

public final class DpConnectorsConfigurationBuilder {
    private Long id;
    private Map<String, Object> connectorConfiguration;
    private Integer tableId;
    private String type;
    private String kafkaClusterName;

    private DpConnectorsConfigurationBuilder() {
    }

    public static DpConnectorsConfigurationBuilder getInstance() {
        return new DpConnectorsConfigurationBuilder();
    }

    public DpConnectorsConfigurationBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpConnectorsConfigurationBuilder withConnectorConfiguration(Map<String, Object> connectorConfiguration) {
        this.connectorConfiguration = connectorConfiguration;
        return this;
    }

    public DpConnectorsConfigurationBuilder withTableId(Integer tableId) {
        this.tableId = tableId;
        return this;
    }

    public DpConnectorsConfigurationBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public DpConnectorsConfigurationBuilder withKafkaClusterName(String kafkaClusterName) {
        this.kafkaClusterName = kafkaClusterName;
        return this;
    }

    public DpConnectorsConfiguration build() {
        DpConnectorsConfiguration dpConnectorsConfiguration = new DpConnectorsConfiguration();
        dpConnectorsConfiguration.setId(id);
        dpConnectorsConfiguration.setConnectorConfiguration(connectorConfiguration);
        dpConnectorsConfiguration.setTableId(tableId);
        dpConnectorsConfiguration.setType(type);
        dpConnectorsConfiguration.setKafkaClusterName(kafkaClusterName);
        return dpConnectorsConfiguration;
    }
}
