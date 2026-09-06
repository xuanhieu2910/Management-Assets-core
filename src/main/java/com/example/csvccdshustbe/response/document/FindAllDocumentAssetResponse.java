package com.example.csvccdshustbe.response.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllDocumentAssetResponse {

    @JsonProperty("code_type_process")
    private String codeTypeProcess;
    @JsonProperty("name_type_process")
    private String nameTypeProcess;
    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("name_user_create")
    private String nameUserCreate;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("description")
    private String description;

}
