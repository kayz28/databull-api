package com.databull.api.dto.requests;

import com.databull.api.dto.DestinationDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import com.databull.api.dto.TableDetails;

@Data
public class TableSyncRequest {

    @NotNull
    Long dsId;

    @NotEmpty
    String databaseName;

    String dstDatabaseName;

    String dsType;

    @NotNull
    Long dstId;

    String dstType;

    @NotNull
    Long orgId;

    @NotEmpty public List<TableDetails> tableDetailsList;
}
