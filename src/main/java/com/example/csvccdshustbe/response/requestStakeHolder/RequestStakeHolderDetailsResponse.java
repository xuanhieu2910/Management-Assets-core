package com.example.csvccdshustbe.response.requestStakeHolder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStakeHolderDetailsResponse {

    @JsonProperty("id_request_stake_holder")
    private Integer idRequestStakeHolder;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("description")
    private String description;

}
