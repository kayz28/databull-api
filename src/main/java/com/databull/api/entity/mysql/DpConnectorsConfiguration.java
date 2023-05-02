package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpConnectorsConfigurationBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "dp_connectors_configuration", schema = "data_platform")
@Getter
@Setter
public class DpConnectorsConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "connector_configuration", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> connectorConfiguration;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "type", length = 6)
    private String type;

    @Column(name = "kafka_cluster_name", length = 100)
    private String kafkaClusterName;

    public static DpConnectorsConfigurationBuilder builder() {
        return DpConnectorsConfigurationBuilder.getInstance();
    }

}