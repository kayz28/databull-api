package com.databull.api.entity.mysql.builders;

import com.databull.api.entity.mysql.DpSchemaMapper;

import java.time.Instant;

public final class DpSchemaMapperBuilder {
    private Long id;
    private String sourceName;
    private String version;
    private String syncName;
    private String sourceDatatype;
    private String sinkDatatype;
    private String integerationCategory;
    private Instant createdAt;
    private Instant updatedAt;

    private DpSchemaMapperBuilder() {
    }

    public static DpSchemaMapperBuilder getInstance() {
        return new DpSchemaMapperBuilder();
    }

    public DpSchemaMapperBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DpSchemaMapperBuilder withSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public DpSchemaMapperBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public DpSchemaMapperBuilder withSyncName(String syncName) {
        this.syncName = syncName;
        return this;
    }

    public DpSchemaMapperBuilder withSourceDatatype(String sourceDatatype) {
        this.sourceDatatype = sourceDatatype;
        return this;
    }

    public DpSchemaMapperBuilder withSinkDatatype(String sinkDatatype) {
        this.sinkDatatype = sinkDatatype;
        return this;
    }

    public DpSchemaMapperBuilder withIntegerationCategory(String integerationCategory) {
        this.integerationCategory = integerationCategory;
        return this;
    }

    public DpSchemaMapperBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DpSchemaMapperBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DpSchemaMapper build() {
        DpSchemaMapper dpSchemaMapper = new DpSchemaMapper();
        dpSchemaMapper.setId(id);
        dpSchemaMapper.setSourceName(sourceName);
        dpSchemaMapper.setVersion(version);
        dpSchemaMapper.setSyncName(syncName);
        dpSchemaMapper.setSourceDatatype(sourceDatatype);
        dpSchemaMapper.setSinkDatatype(sinkDatatype);
        dpSchemaMapper.setIntegerationCategory(integerationCategory);
        dpSchemaMapper.setCreatedAt(createdAt);
        dpSchemaMapper.setUpdatedAt(updatedAt);
        return dpSchemaMapper;
    }
}
