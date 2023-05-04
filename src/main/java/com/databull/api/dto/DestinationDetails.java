package com.databull.api.dto;

import com.databull.api.utils.rest.Pair;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinationDetails {

    private String username;
    private String password;
    private Integer port;
    private String endpoint;
    private String databaseName;

    private Long dstId;

    private Boolean isDestinationWarehouse;
    private Boolean isDestinationDataStore;

}

