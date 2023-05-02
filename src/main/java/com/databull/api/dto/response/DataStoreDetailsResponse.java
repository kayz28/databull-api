package com.databull.api.dto.response;

import lombok.*;

import java.util.List;

//database details
@Data
public class DataStoreDetailsResponse {
   Long dsId;
   Integer dbCount;
   String dsType;
   List<String> databaseNameList;
}
