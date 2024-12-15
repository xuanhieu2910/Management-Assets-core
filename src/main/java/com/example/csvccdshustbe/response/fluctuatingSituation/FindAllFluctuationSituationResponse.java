package com.example.csvccdshustbe.response.fluctuatingSituation;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllFluctuationSituationResponse {

    @JsonProperty("id_fluctuating_situation")
    private Integer idFluctuatingSituation;
    @JsonProperty("id_process")
    private Integer idProcess;
    @JsonProperty("status")
    private Integer status;
    @JsonSetter("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("id_user_modified")
    private Integer idUserModified;
    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("id_document")
    private Integer idDocument;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("time_inventory")
    private String timeInventory;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;

}
