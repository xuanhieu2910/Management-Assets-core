package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessAssetResponse {

    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("id_user_create")
    private Integer idUserCreate;
    @JsonProperty("code_user_create")
    private String codeUserCreate;
    @JsonProperty("name_user_create")
    private String nameUserCreate;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("code_user_create")
    private String timeCreated;
    @JsonProperty("code_user_create")
    private String timeModified;
    @JsonProperty("code_user_create")
    private String timeDocument;
    @JsonProperty("code_user_create")
    private String timeIncrease;
}
