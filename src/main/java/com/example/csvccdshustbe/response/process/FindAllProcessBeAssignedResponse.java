package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessBeAssignedResponse {

    @JsonProperty("id_process")
    private Integer idProcess;
    @JsonProperty("id_document")
    private Integer idDocument;
    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("type_process")
    private String typeProcess;
    @JsonProperty("description")
    private String description;
    @JsonProperty("time_created_process")
    private String timeCreatedProcess;
    @JsonProperty("time_modified_process")
    private String timeModifiedProcess;
    @JsonProperty("user_name_created")
    private String userNameCreated;
    @JsonProperty("full_name_created")
    private String fullNameCreated;
    @JsonProperty("time_created_document")
    private String timeCreatedDocument;
    @JsonProperty("time_modified_document")
    private String timeModifiedDocument;
    @JsonProperty("time_increase")
    private String timeIncrease;
    @JsonProperty("time_document")
    private String timeDocument;
    @JsonProperty("id_state")
    private Integer idState;
    @JsonProperty("id_type_state")
    private Integer idTypeState;
    @JsonProperty("code_type_state")
    private String codeTypeState;
    @JsonProperty("status_state")
    private Integer statusState;
    @JsonProperty("id_request_stake_holder")
    private Integer idRequestStakeHolder;
}
