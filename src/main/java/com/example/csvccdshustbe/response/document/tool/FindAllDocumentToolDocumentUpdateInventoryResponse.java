package com.example.csvccdshustbe.response.document.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllDocumentToolDocumentUpdateInventoryResponse {

    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("id_user_create")
    private Integer idUserCreate;
    @JsonProperty("full_name_create")
    private String fullNameCreate;
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
    @JsonProperty("time_update_inventory")
    private String timeUpdateInventory;
    @JsonProperty("description")
    private String description;

}
