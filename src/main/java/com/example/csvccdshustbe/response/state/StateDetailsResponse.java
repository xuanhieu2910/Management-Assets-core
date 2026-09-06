package com.example.csvccdshustbe.response.state;

import com.example.csvccdshustbe.response.request.RequestDetailsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StateDetailsResponse {

    @JsonProperty("id_state")
    private Integer idState;
    @JsonProperty("status_state")
    private Integer statusState;
    @JsonProperty("id_type_state")
    private Integer idTypeState;
    @JsonProperty("code_type_state")
    private String codeTypeState;
    @JsonProperty("name_type_state")
    private String nameTypeState;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("id_process")
    private Integer idProcess;
    @JsonProperty("request")
    private List<RequestDetailsResponse> request;
}
