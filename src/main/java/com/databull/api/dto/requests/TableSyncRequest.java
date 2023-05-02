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

    @NotEmpty
    Long dsId;

    @NotEmpty
    String databaseName;

    @NotEmpty(message = "Datastore-type can not be empty.Value must be in mysql, postgres, mongodb")
    String dsType;

    @NotEmpty
    DestinationDetails destinationDetails;

    @NotEmpty
    Long orgId;

    @NotNull @Valid public List<TableDetails> tableDetailsList;
}
