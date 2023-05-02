package com.databull.api.dto.response;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.TableDetails;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
public class TableDetailsResponse {

    String dbName;
    String tableName;
    String dsType;
    Long dsId;
    Long numberOfColumns;
    List<ColumnDetails> columnDetails;
}
