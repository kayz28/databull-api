package com.databull.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TableDetails {

    @NotEmpty
    String tableName;
    @NotEmpty
    String tableComment;
    @NotEmpty
    String databaseName;
    @NotEmpty
    String dsType;

    @NotEmpty
    List<ColumnDetails> columnDetailsList;

}