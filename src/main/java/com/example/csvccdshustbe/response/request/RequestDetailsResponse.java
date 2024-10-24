package com.example.csvccdshustbe.response.request;

import com.example.csvccdshustbe.response.requestData.RequestDataDetailsResponse;
import com.example.csvccdshustbe.response.requestStakeHolder.RequestStakeHolderDetailsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDetailsResponse {

    @JsonProperty("id_request")
    private Integer idRequest;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("request_data")
    private List<RequestDataDetailsResponse> requestData;
    @JsonProperty("request_stake_holder")
    private List<RequestStakeHolderDetailsResponse> requestStakeHolder;
}
