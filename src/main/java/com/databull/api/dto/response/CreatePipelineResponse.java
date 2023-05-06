package com.databull.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class CreatePipelineResponse {
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer statusCode;
}
