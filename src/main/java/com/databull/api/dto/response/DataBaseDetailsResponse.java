package com.databull.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
//table details
@Data
public class DataBaseDetailsResponse {

    String databaseName;
    List<TableDetails> tableDetailsList;
    Long dsId;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TableDetails {
        String tableName;
        String tableComment;
        Long numberOfRecords;
    }
}
