package com.databull.api.entity.mysql;

import com.databull.api.entity.mysql.builders.DpDestinationTableMetadatumBuilder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "dp_destination_table_metadata", schema = "data_platform")
@Getter
@Setter
public class DpDestinationTableMetadatum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "destination_table_name")
    private String destinationTableName;

    @Column(name = "s3_path_topic", length = 500)
    private String s3PathTopic;

    @Column(name = "s3_path_schema", length = 500)
    private String s3PathSchema;

    @Column(name = "update_frequency")
    private Integer updateFrequency;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_merge_increment")
    private Integer lastMergeIncrement;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "spark_conf")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> sparkConf;

    @Column(name = "s3_bucket_name", length = 100)
    private String s3BucketName;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public static DpDestinationTableMetadatumBuilder builder() {
        return DpDestinationTableMetadatumBuilder.getInstance();
    }
}