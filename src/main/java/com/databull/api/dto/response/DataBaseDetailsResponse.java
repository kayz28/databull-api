package com.databull.api.dto.response;

import lombok.Data;

import java.util.List;
//table details
@Data
public class DataBaseDetailsResponse {

    String databaseName;
    List<TableDetails> tableDetailsList;
    Long dsId;

    @Data
    public static class TableDetails {
        String tableName;
        String tableComment;
        Long numberOfRecords;
    }
}
