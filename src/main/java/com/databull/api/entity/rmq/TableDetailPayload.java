package com.databull.api.entity.rmq;

import com.databull.api.dto.ColumnDetails;
import com.databull.api.dto.DestinationDetails;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
public class TableDetailPayload {
    private String tableName;
    private String databaseName;
    private Long dsId;
    private UUID uuid;
    private Instant timestamp;
    private List<ColumnDetails> columnDetailsList;
    private String orgName;
    private String dsType;

    private JSONObject sourceConnector;
    private JSONObject sinkConnector;
    private DestinationDetails destinationDetails;
}
