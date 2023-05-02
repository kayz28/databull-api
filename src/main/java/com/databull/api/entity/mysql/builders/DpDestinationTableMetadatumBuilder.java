package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpDestinationTableMetadatum;

import java.time.Instant;
import java.util.Map;

public final class DpDestinationTableMetadatumBuilder {
    private Long id;
    private String destinationTableName;
    private String s3PathTopic;
    private String s3PathSchema;
    private Integer updateFrequency;
    private Boolean isActive;
    private Integer lastMergeIncrement;
    private Long tableId;
    private Map<String, Object> sparkConf;
    private String s3BucketName;
    private Instant createdAt;
    private Instant updatedAt;

    private DpDestinationTableMetadatumBuilder() {
    }

    public static DpDestinationTableMetadatumBuilder getInstance() {
        return new DpDestinationTableMetadatumBuilder();
    }

    public DpDestinationTableMetadatumBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withDestinationTableName(String destinationTableName) {
        this.destinationTableName = destinationTableName;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withS3PathTopic(String s3PathTopic) {
        this.s3PathTopic = s3PathTopic;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withS3PathSchema(String s3PathSchema) {
        this.s3PathSchema = s3PathSchema;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withUpdateFrequency(Integer updateFrequency) {
        this.updateFrequency = updateFrequency;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withLastMergeIncrement(Integer lastMergeIncrement) {
        this.lastMergeIncrement = lastMergeIncrement;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withTableId(Long tableId) {
        this.tableId = tableId;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withSparkConf(Map<String, Object> sparkConf) {
        this.sparkConf = sparkConf;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DpDestinationTableMetadatumBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DpDestinationTableMetadatum build() {
        DpDestinationTableMetadatum dpDestinationTableMetadatum = new DpDestinationTableMetadatum();
        dpDestinationTableMetadatum.setId(id);
        dpDestinationTableMetadatum.setDestinationTableName(destinationTableName);
        dpDestinationTableMetadatum.setS3PathTopic(s3PathTopic);
        dpDestinationTableMetadatum.setS3PathSchema(s3PathSchema);
        dpDestinationTableMetadatum.setUpdateFrequency(updateFrequency);
        dpDestinationTableMetadatum.setIsActive(isActive);
        dpDestinationTableMetadatum.setLastMergeIncrement(lastMergeIncrement);
        dpDestinationTableMetadatum.setTableId(tableId);
        dpDestinationTableMetadatum.setSparkConf(sparkConf);
        dpDestinationTableMetadatum.setS3BucketName(s3BucketName);
        dpDestinationTableMetadatum.setCreatedAt(createdAt);
        dpDestinationTableMetadatum.setUpdatedAt(updatedAt);
        return dpDestinationTableMetadatum;
    }
}
