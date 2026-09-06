package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessAssetUpdateInventoryResponse {

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
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("time_document")
    private String timeDocument;
    @JsonProperty("time_inventory")
    private String timeInventory;
    @JsonProperty("id_process")
    private Integer idProcess;
}
