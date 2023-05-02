package com.databull.api.dto;

import com.databull.api.utils.rest.Pair;
import lombok.Data;

@Data
public class DestinationDetails {

    private String username;
    private String password;
    private Integer port;
    private String endpoint;
    private String databaseName;
    private String orgId;
    private String orgName;

    private Pair<String, Boolean> dataStoreTypeAndCheck;
    private Pair<String, Boolean> dataWareHouseTypeAndCheck;

}

