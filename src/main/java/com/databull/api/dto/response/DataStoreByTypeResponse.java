package com.databull.api.dto.response;

import lombok.Data;

@Data
public class DataStoreByTypeResponse {
    private String username;
    private String endpoint;
    private Long port;
    private String dsSName;
    private Long dsId;
}