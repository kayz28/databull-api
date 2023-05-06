package com.databull.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
