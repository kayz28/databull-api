package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpSchemaMapperBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "dp_schema_mappers", schema = "data_platform")
@Getter
@Setter
public class DpSchemaMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "source_name", nullable = false, length = 50)
    private String sourceName;

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "sync_name", nullable = false, length = 50)
    private String syncName;

    @Column(name = "source_datatype", length = 20)
    private String sourceDatatype;

    @Column(name = "sink_datatype", length = 20)
    private String sinkDatatype;

    @Column(name = "Integeration_category", length = 100)
    private String integerationCategory;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public static DpSchemaMapperBuilder builder() {
        return DpSchemaMapperBuilder.getInstance();
    }

}