package com.example.csvccdshustbe.response.document;


import com.example.csvccdshustbe.response.state.BluePrintStateResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindDetailsDocumentResponse {

    @JsonProperty("id_document")
    private Integer idDocument;
    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("time_increase")
    private String timeIncrease;
    @JsonProperty("time_document")
    private String timeDocument;
    @JsonProperty("states")
    private List<BluePrintStateResponse> states;

}
