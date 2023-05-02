package com.databull.api.dto.response;

import com.databull.api.dto.ColumnDetails;
import lombok.Data;

import java.util.List;

@Data
public class ColumnDetailsResponse {

    String tableName;
    String dsId;
    String dsType;
    Long numberOfColumns;

    List<ColumnDetails> columnDetailsList;

}
