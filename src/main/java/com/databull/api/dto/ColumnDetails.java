package com.databull.api.dto;

import lombok.Data;

@Data
public class ColumnDetails {
    String columnName;
    String columnType;
    String dataType;
    String isNullable;
    String columnComment;
    Boolean isIncrementingColumn;
    Boolean isPartitionColumn;
    Boolean isTimestampColumn;
}
