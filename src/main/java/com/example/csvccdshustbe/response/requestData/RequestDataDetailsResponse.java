package com.example.csvccdshustbe.response.requestData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDataDetailsResponse {

    @JsonProperty("id_request_data")
    private Integer idRequestData;
    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    @JsonProperty("status")
    private Integer status;
}
